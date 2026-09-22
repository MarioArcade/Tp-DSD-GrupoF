package com.rentar.rentar.controller;

import com.rentar.rentar.dto.CrearReservaRequest;
import com.rentar.rentar.modelos.Reserva;
import com.rentar.rentar.servicios.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Crear reserva
    @PostMapping
    public ResponseEntity<Reserva> crear(
            @RequestBody CrearReservaRequest request) {

        Reserva reserva = reservaService.crear(
                request.getClienteId(),
                request.getVehiculoId(),
                request.getFechaInicio(),
                request.getFechaFin()
        );

        return ResponseEntity.ok(reserva);
    }

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodas() {

        return ResponseEntity.ok(
                reservaService.obtenerTodas()
        );
    }

    // Obtener una
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.obtenerPorId(id)
        );
    }

    // Cancelar
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.cancelar(id)
        );
    }
}