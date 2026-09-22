package com.rentar.rentar.Repository;

import com.rentar.rentar.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByDocumentoAndIdNot(String documento, Long id);
}
