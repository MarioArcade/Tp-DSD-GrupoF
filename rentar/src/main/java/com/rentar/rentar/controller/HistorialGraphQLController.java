package com.rentar.rentar.controller;

import com.rentar.rentar.dto.HistorialAlquilerDTO;
import com.rentar.rentar.servicios.ReservaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HistorialGraphQLController {

    private final ReservaService reservaService;

    public HistorialGraphQLController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @QueryMapping
    public List<HistorialAlquilerDTO> historialAlquileres(
            @Argument Long clienteId) {

        return reservaService.obtenerHistorial(clienteId);
    }
}