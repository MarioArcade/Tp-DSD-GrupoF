package com.rentar.rentar.Repository;
import com.rentar.rentar.enums.EstadoReserva;
import com.rentar.rentar.enums.TipoVehiculo;
import com.rentar.rentar.modelos.Reserva;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("""
    SELECT r
    FROM Reserva r
    WHERE (:clienteId IS NULL OR r.cliente.id = :clienteId)
      AND (:vehiculoId IS NULL OR r.vehiculo.id = :vehiculoId)
      AND (:tipo IS NULL OR r.vehiculo.tipo = :tipo)
      AND (:estado IS NULL OR r.estado = :estado)
      AND (:fechaDesde IS NULL OR r.fechaFin >= :fechaDesde)
      AND (:fechaHasta IS NULL OR r.fechaInicio <= :fechaHasta)
    ORDER BY r.fechaInicio DESC
""")
    List<Reserva> buscarConFiltros(
        @Param("clienteId") Long clienteId,
        @Param("vehiculoId") Long vehiculoId,
        @Param("tipo") TipoVehiculo tipo,
        @Param("estado") EstadoReserva estado,
        @Param("fechaDesde") LocalDateTime fechaDesde,
        @Param("fechaHasta") LocalDateTime fechaHasta
);

@Query("""
    SELECT r
    FROM Reserva r
    WHERE r.cliente.id = :clienteId
      AND (
          r.estado = :cancelada
          OR r.fechaFin <= :ahora
      )
    ORDER BY r.fechaInicio DESC
""")
List<Reserva> buscarHistorial(
        @Param("clienteId") Long clienteId,
        @Param("cancelada") EstadoReserva cancelada,
        @Param("ahora") LocalDateTime ahora
);
List<Reserva> findByClienteEmail(String email);
}