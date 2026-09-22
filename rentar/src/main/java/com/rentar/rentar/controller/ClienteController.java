package com.rentar.rentar.controller;

import com.rentar.rentar.modelos.Cliente;
import com.rentar.rentar.servicios.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Crear cliente
    @PostMapping
    public ResponseEntity<Cliente> crear(
            @RequestBody Cliente cliente) {

        return ResponseEntity.ok(
                clienteService.crear(cliente)
        );
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {

        return ResponseEntity.ok(
                clienteService.obtenerTodos()
        );
    }

    // Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.obtenerPorId(id)
        );
    }

    // Modificar
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> modificar(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        return ResponseEntity.ok(
                clienteService.modificar(id, cliente)
        );
    }

    // Baja lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        clienteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}