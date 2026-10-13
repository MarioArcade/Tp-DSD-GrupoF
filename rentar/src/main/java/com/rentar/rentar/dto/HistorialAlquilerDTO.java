package com.rentar.rentar.dto;

import com.rentar.rentar.enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialAlquilerDTO {

    private Long reservaId;
    private String patente;
    private String marca;
    private String modelo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private long dias;
    private BigDecimal importeTotal;
    private EstadoReserva estado;

    public HistorialAlquilerDTO(
            Long reservaId,
            String patente,
            String marca,
            String modelo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            long dias,
            BigDecimal importeTotal,
            EstadoReserva estado) {

        this.reservaId = reservaId;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dias = dias;
        this.importeTotal = importeTotal;
        this.estado = estado;
    }

    public Long getReservaId() { return reservaId; }
    public String getPatente() { return patente; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public long getDias() { return dias; }
    public BigDecimal getImporteTotal() { return importeTotal; }
    public EstadoReserva getEstado() { return estado; }
}