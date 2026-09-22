package com.rentar.rentar.Repository;
import com.rentar.rentar.enums.EstadoReserva;
import com.rentar.rentar.modelos.Reserva;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

     @Query("""
        SELECT COUNT(r) > 0
        FROM Reserva r
        WHERE r.vehiculo.id = :vehiculoId
          AND r.estado = :estado
          AND r.fechaInicio < :fechaFin
          AND r.fechaFin > :fechaInicio
    """)
    boolean existeReservaSuperpuesta(
            @Param("vehiculoId") Long vehiculoId,
            @Param("estado") EstadoReserva estado,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}