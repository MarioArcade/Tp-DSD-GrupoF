
package com.rentar.rentar.servicios;

import com.rentar.rentar.Repository.ClienteRepository;
import com.rentar.rentar.modelos.Cliente;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteUserDetailsService {

    private final ClienteRepository clienteRepository;

    public ClienteUserDetailsService(
            ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public UserDetails loadClientByEmail(String email) {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "No existe un cliente con ese email"));

        if (!cliente.isActivo()) {
            throw new UsernameNotFoundException(
                    "El cliente está inactivo");
        }

        if (cliente.getPasswordHash() == null
                || cliente.getPasswordHash().isBlank()) {
            throw new UsernameNotFoundException(
                    "El cliente no tiene una contraseña configurada");
        }

        return User.withUsername(cliente.getEmail())
                .password(cliente.getPasswordHash())
                .roles("CLIENT")
                .build();
    }
}
