package com.rentar.rentar.servicios;

import com.rentar.rentar.modelos.Cliente;
import com.rentar.rentar.modelos.Reserva;
import com.rentar.rentar.modelos.Vehiculo;
import com.rentar.rentar.enums.EstadoReserva;
import com.rentar.rentar.enums.TipoVehiculo;
import com.rentar.rentar.Repository.ClienteRepository;
import com.rentar.rentar.Repository.ReservaRepository;
import com.rentar.rentar.Repository.VehiculoRepository;
import com.rentar.rentar.dto.HistorialAlquilerDTO;
import com.rentar.rentar.dto.ReservaConsultaDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            ClienteRepository clienteRepository,
            VehiculoRepository vehiculoRepository) {

        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    private Authentication autenticacionActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
                throw new AccessDeniedException("Debe autenticarse para realizar esta operación");
        }
        return auth;
    }

    private boolean esAdministrador(Authentication auth) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    private Cliente clienteAutenticado(Authentication auth) {
        return clienteRepository.findByEmail(auth.getName())
        .filter(Cliente::isActivo).orElseThrow(() -> new AccessDeniedException("No se encontró un cliente activo para este usuario"));

    }
    private void verificarPropiedad(Reserva reserva, Authentication auth) {
        if (!esAdministrador(auth)&& !reserva.getCliente().getEmail().equals(auth.getName())){
                throw new AccessDeniedException( "No tenés permiso para acceder a esta reserva");  
        }
    }
        
    public Reserva crear(
            Long clienteId,
            Long vehiculoId,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        
        Authentication auth = autenticacionActual();
        if (!esAdministrador(auth)) {
                clienteId = clienteAutenticado(auth).getId();
        }
        //Realizamos una consulta para ver si el cliente esta cargado
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new RuntimeException("No existe el cliente"));

        // Compruebo que ese cliente este activo
        if (!cliente.isActivo()) {
            throw new RuntimeException(
                    " cliente inactivo");
        }

        //Realizo la consulta de vehiculo
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() ->
                        new RuntimeException("No existe el vehículo"));

        //Confirmo que este se encuentre activo
        if (!vehiculo.isActivo()) {
            throw new RuntimeException(
                    "vehículo inactivo");
        }

        // compruebo que la fecha de reserva no sea anterior a la actual
        if (!fechaInicio.isAfter(LocalDateTime.now())) {
            throw new RuntimeException(
                    "fecha incorrecta");
        }

        // verifico que no se pueda devolver antes de la fecha de alquiler
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new RuntimeException(
                    "fecha incorrecta");
        }

        // compruebo si esta disponivle el vehiculo para alquilar 
        boolean existeSuperposicion =
                reservaRepository.existeReservaSuperpuesta(
                        vehiculoId,
                        EstadoReserva.CONFIRMADA,
                        fechaInicio,
                        fechaFin
                );

        if (existeSuperposicion) {
            throw new RuntimeException(
                    "El vehículo ya está reservado durante ese período");
        }

        // calculo la cantidad de dias
        long minutos = Duration.between(
                fechaInicio,
                fechaFin
        ).toMinutes();

        long dias = (minutos + 1439) / 1440;

        // calculo el importe total
        BigDecimal importeTotal =
                vehiculo.getPrecioDiario()
                        .multiply(BigDecimal.valueOf(dias));

        // Genero la reserva
        Reserva reserva = new Reserva();

        reserva.setCliente(cliente);
        reserva.setVehiculo(vehiculo);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setImporteTotal(importeTotal);
        reserva.setEstado(EstadoReserva.CONFIRMADA);

        // Guardo en la db
        return reservaRepository.save(reserva);
    }

    public Reserva cancelar(Long reservaId) {
        
    Reserva reserva = reservaRepository.findById(reservaId).orElseThrow(() ->new RuntimeException("No existe la reserva"));
    verificarPropiedad(reserva, autenticacionActual());
    if (reserva.getEstado() == EstadoReserva.CANCELADA) {
        throw new RuntimeException(
                "La reserva ya está cancelada");
    }

    if (!LocalDateTime.now().isBefore(reserva.getFechaInicio())) {
        throw new RuntimeException(
                "La reserva ya no puede ser cancelada");
    }

    reserva.setEstado(EstadoReserva.CANCELADA);

    return reservaRepository.save(reserva);
}
//ObtenerTodas
public List<Reserva> obtenerTodas() {
        Authentication auth = autenticacionActual();
         if (esAdministrador(auth)) {
                return reservaRepository.findAll();
         }        
    return reservaRepository.findAll().stream().filter(r -> r.getCliente().getEmail().equals(auth.getName())).toList();
}

public Reserva obtenerPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() ->new RuntimeException("No existe la reserva con id " + id));
        verificarPropiedad(reserva, autenticacionActual());
        return reserva;
}


public List<ReservaConsultaDTO> buscarConFiltros(
        Long clienteId,
        Long vehiculoId,
        TipoVehiculo tipo,
        EstadoReserva estado,
        LocalDateTime fechaDesde,
        LocalDateTime fechaHasta) {
    Authentication auth = autenticacionActual();
    if (!esAdministrador(auth)) {
            clienteId = clienteAutenticado(auth).getId();
        }

    if (fechaDesde != null
            && fechaHasta != null
            && fechaHasta.isBefore(fechaDesde)) {
        throw new RuntimeException(
                "La fecha hasta no puede ser anterior a la fecha desde");
    }

    List<Reserva> reservas = reservaRepository.buscarConFiltros(
            clienteId,
            vehiculoId,
            tipo,
            estado,
            fechaDesde,
            fechaHasta
    );

    return reservas.stream()
            .map(r -> new ReservaConsultaDTO(
                    r.getId(),
                    r.getCliente().getId(),
                    r.getCliente().getNombre(),
                    r.getCliente().getApellido(),
                    r.getVehiculo().getId(),
                    r.getVehiculo().getPatente(),
                    r.getVehiculo().getMarca(),
                    r.getVehiculo().getModelo(),
                    r.getVehiculo().getTipo(),
                    r.getFechaInicio(),
                    r.getFechaFin(),
                    r.getVehiculo().getPrecioDiario(),
                    r.getImporteTotal(),
                    r.getEstado()
            ))
            .toList();
}

public List<HistorialAlquilerDTO> obtenerHistorial(Long clienteId) {
        Authentication auth = autenticacionActual();
        if (!esAdministrador(auth)) {
                clienteId = clienteAutenticado(auth).getId();
        }

    Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("No existe el cliente"));

    List<Reserva> reservas = reservaRepository.buscarHistorial(
            clienteId,
            EstadoReserva.CANCELADA,
            LocalDateTime.now()
    );

    return reservas.stream()
            .map(r -> {
                long minutos = Duration.between(
                        r.getFechaInicio(),
                        r.getFechaFin()
                ).toMinutes();

                long dias = (minutos + 1439) / 1440;

                return new HistorialAlquilerDTO(
                        r.getId(),
                        r.getVehiculo().getPatente(),
                        r.getVehiculo().getMarca(),
                        r.getVehiculo().getModelo(),
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        dias,
                        r.getImporteTotal(),
                        r.getEstado()
                );
            })
            .toList();
}
}