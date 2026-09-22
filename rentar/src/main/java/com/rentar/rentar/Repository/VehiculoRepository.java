package com.rentar.rentar.Repository;
import com.rentar.rentar.modelos.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPatente(String patente);
}
