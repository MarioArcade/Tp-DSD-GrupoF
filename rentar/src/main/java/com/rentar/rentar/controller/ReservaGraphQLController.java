package com.rentar.rentar.controller;

import com.rentar.rentar.dto.ReservaConsultaDTO;
import com.rentar.rentar.enums.EstadoReserva;
import com.rentar.rentar.enums.TipoVehiculo;
import com.rentar.rentar.servicios.ReservaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReservaGraphQLController {

    private final ReservaService reservaService;

    public ReservaGraphQLController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @QueryMapping
    public List<ReservaConsultaDTO> reservas(
            @Argument Long clienteId,
            @Argument Long vehiculoId,
            @Argument TipoVehiculo tipo,
            @Argument EstadoReserva estado,
            @Argument String fechaDesde,
            @Argument String fechaHasta) {

        LocalDateTime desde = fechaDesde == null
                ? null
                : LocalDateTime.parse(fechaDesde);

        LocalDateTime hasta = fechaHasta == null
                ? null
                : LocalDateTime.parse(fechaHasta);

        return reservaService.buscarConFiltros(
                clienteId,
                vehiculoId,
                tipo,
                estado,
                desde,
                hasta
        );
    }
}