package com.rentar.rentar.servicios;
import com.rentar.rentar.modelos.Vehiculo;
import com.rentar.rentar.enums.EstadoVehiculo;
import com.rentar.rentar.enums.TipoVehiculo;
import com.rentar.rentar.Repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public Vehiculo crear(Vehiculo vehiculo) {

        if (vehiculoRepository.existsByPatente(vehiculo.getPatente())) {
            throw new RuntimeException("Patente y ingresada");
        }

        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        vehiculo.setActivo(true);

        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo obtenerPorId(Long id) {

        return vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("No existe el vehículo con id " + id));
    }

    public Vehiculo modificar(Long id, Vehiculo datos) {

        Vehiculo vehiculo = obtenerPorId(id);

        vehiculo.setMarca(datos.getMarca());
        vehiculo.setModelo(datos.getModelo());
        vehiculo.setAnio(datos.getAnio());
        vehiculo.setColor(datos.getColor());
        vehiculo.setTipo(datos.getTipo());
        vehiculo.setPrecioDiario(datos.getPrecioDiario());
        vehiculo.setActivo(datos.isActivo());

        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Long id) {

        Vehiculo vehiculo = obtenerPorId(id);

        vehiculo.setActivo(false);

        vehiculoRepository.save(vehiculo);
    }
    public List<Vehiculo> buscarDisponibles(
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        TipoVehiculo tipo,
        String marca,
        String modelo,
        BigDecimal precioMinimo,
        BigDecimal precioMaximo) {

    if (!fechaFin.isAfter(fechaInicio)) {
        throw new RuntimeException(
                "La fecha de fin debe ser posterior a la fecha de inicio");
    }

    return vehiculoRepository.buscarDisponibles(
            fechaInicio,
            fechaFin,
            tipo,
            marca,
            modelo,
            precioMinimo,
            precioMaximo
    );
}
}
