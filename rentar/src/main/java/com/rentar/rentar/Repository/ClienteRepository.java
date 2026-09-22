package com.rentar.rentar.Repository;

import com.rentar.rentar.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
}
