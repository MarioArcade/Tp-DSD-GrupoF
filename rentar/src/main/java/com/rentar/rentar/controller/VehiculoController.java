package com.rentar.rentar.controller;

import com.rentar.rentar.modelos.Vehiculo;
import com.rentar.rentar.servicios.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // Crear vehículo
    @PostMapping
    public ResponseEntity<Vehiculo> crear(
            @RequestBody Vehiculo vehiculo) {

        return ResponseEntity.ok(
                vehiculoService.crear(vehiculo)
        );
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {

        return ResponseEntity.ok(
                vehiculoService.obtenerTodos()
        );
    }

    // Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vehiculoService.obtenerPorId(id)
        );
    }

    // Modificar
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> modificar(
            @PathVariable Long id,
            @RequestBody Vehiculo vehiculo) {

        return ResponseEntity.ok(
                vehiculoService.modificar(id, vehiculo)
        );
    }

    // Baja lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        vehiculoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}