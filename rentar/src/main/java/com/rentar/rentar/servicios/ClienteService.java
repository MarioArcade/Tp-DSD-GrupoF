package com.rentar.rentar.servicios;

import com.rentar.rentar.modelos.Cliente;
import com.rentar.rentar.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crear(Cliente cliente) {

        if (clienteRepository.existsByDocumento(cliente.getDocumento())) {
            throw new RuntimeException(
                    "Ya existe un cliente con ese documento"
            );
        }

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException(
                    "Ya existe un cliente con ese email"
            );
        }

        cliente.setActivo(true);

        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {

        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe el cliente con id " + id
                        ));
    }

    public Cliente modificar(Long id, Cliente datos) {

    Cliente cliente = obtenerPorId(id);

    if (clienteRepository.existsByEmailAndIdNot(
            datos.getEmail(), id)) {

        throw new RuntimeException(
                "El email ya esta registrado"
        );
    }

    if (clienteRepository.existsByDocumentoAndIdNot(
            datos.getDocumento(), id)) {

        throw new RuntimeException(
                "El documento ya esta registrado"
        );
    }

    cliente.setDocumento(datos.getDocumento());
    cliente.setNombre(datos.getNombre());
    cliente.setApellido(datos.getApellido());
    cliente.setEmail(datos.getEmail());
    cliente.setTelefono(datos.getTelefono());
    cliente.setFechaNacimiento(datos.getFechaNacimiento());

    return clienteRepository.save(cliente);
}

    public void eliminar(Long id) {

        Cliente cliente = obtenerPorId(id);

        cliente.setActivo(false);

        clienteRepository.save(cliente);
    }
}