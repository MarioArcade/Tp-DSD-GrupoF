package com.rentar.rentar.servicios;

import com.rentar.rentar.modelos.Cliente;
import com.rentar.rentar.modelos.Reserva;
import com.rentar.rentar.modelos.Vehiculo;
import com.rentar.rentar.enums.EstadoReserva;

import com.rentar.rentar.Repository.ClienteRepository;
import com.rentar.rentar.Repository.ReservaRepository;
import com.rentar.rentar.Repository.VehiculoRepository;
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

    public Reserva crear(
            Long clienteId,
            Long vehiculoId,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

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

    Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() ->
                    new RuntimeException("No existe la reserva"));

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

public List<Reserva> obtenerTodas() {
    return reservaRepository.findAll();
}

public Reserva obtenerPorId(Long id) {

    return reservaRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "No existe la reserva con id " + id));
}

}