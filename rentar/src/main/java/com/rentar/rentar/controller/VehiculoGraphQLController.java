package com.rentar.rentar.controller;

import com.rentar.rentar.dto.VehiculoDisponibleDTO;
import com.rentar.rentar.modelos.Vehiculo;
import com.rentar.rentar.enums.TipoVehiculo;
import com.rentar.rentar.servicios.VehiculoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VehiculoGraphQLController {

    private final VehiculoService vehiculoService;

    public VehiculoGraphQLController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @QueryMapping
    public List<VehiculoDisponibleDTO> vehiculosDisponibles(
            @Argument String fechaInicio,
            @Argument String fechaFin,
            @Argument TipoVehiculo tipo,
            @Argument String marca,
            @Argument String modelo,
            @Argument BigDecimal precioMinimo,
            @Argument BigDecimal precioMaximo) {

        LocalDateTime inicio =
                LocalDateTime.parse(fechaInicio);

        LocalDateTime fin =
                LocalDateTime.parse(fechaFin);

        List<Vehiculo> vehiculos =
                vehiculoService.buscarDisponibles(
                        inicio,
                        fin,
                        tipo,
                        marca,
                        modelo,
                        precioMinimo,
                        precioMaximo
                );

        return vehiculos.stream()
                .map(v -> new VehiculoDisponibleDTO(
                        v.getPatente(),
                        v.getMarca(),
                        v.getModelo(),
                        v.getAnio(),
                        v.getColor(),
                        v.getTipo(),
                        v.getPrecioDiario()
                ))
                .toList();
    }
}