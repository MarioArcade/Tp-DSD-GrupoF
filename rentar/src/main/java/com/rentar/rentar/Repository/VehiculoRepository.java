package com.rentar.rentar.Repository;
import com.rentar.rentar.modelos.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rentar.rentar.enums.TipoVehiculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPatente(String patente);

    @Query("""
    SELECT v
    FROM Vehiculo v
    WHERE v.activo = true
      AND (:tipo IS NULL OR v.tipo = :tipo)
      AND (:marca IS NULL OR LOWER(v.marca) = LOWER(:marca))
      AND (:modelo IS NULL OR LOWER(v.modelo) = LOWER(:modelo))
      AND (:precioMinimo IS NULL OR v.precioDiario >= :precioMinimo)
      AND (:precioMaximo IS NULL OR v.precioDiario <= :precioMaximo)
      AND NOT EXISTS (
          SELECT r
          FROM Reserva r
          WHERE r.vehiculo = v
            AND r.estado = com.rentar.rentar.enums.EstadoReserva.CONFIRMADA
            AND r.fechaInicio < :fechaFin
            AND r.fechaFin > :fechaInicio
      )
""")
List<Vehiculo> buscarDisponibles(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        @Param("tipo") TipoVehiculo tipo,
        @Param("marca") String marca,
        @Param("modelo") String modelo,
        @Param("precioMinimo") BigDecimal precioMinimo,
        @Param("precioMaximo") BigDecimal precioMaximo
);
}
