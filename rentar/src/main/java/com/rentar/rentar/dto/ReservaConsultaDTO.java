package com.rentar.rentar.dto;

import com.rentar.rentar.enums.EstadoReserva;
import com.rentar.rentar.enums.TipoVehiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservaConsultaDTO {

    private Long id;
    private Long clienteId;
    private String nombreCliente;
    private String apellidoCliente;
    private Long vehiculoId;
    private String patente;
    private String marca;
    private String modelo;
    private TipoVehiculo tipo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private BigDecimal precioDiario;
    private BigDecimal importeTotal;
    private EstadoReserva estado;

    public ReservaConsultaDTO(
            Long id,
            Long clienteId,
            String nombreCliente,
            String apellidoCliente,
            Long vehiculoId,
            String patente,
            String marca,
            String modelo,
            TipoVehiculo tipo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            BigDecimal precioDiario,
            BigDecimal importeTotal,
            EstadoReserva estado) {

        this.id = id;
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.vehiculoId = vehiculoId;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioDiario = precioDiario;
        this.importeTotal = importeTotal;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getNombreCliente() { return nombreCliente; }
    public String getApellidoCliente() { return apellidoCliente; }
    public Long getVehiculoId() { return vehiculoId; }
    public String getPatente() { return patente; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public TipoVehiculo getTipo() { return tipo; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public BigDecimal getPrecioDiario() { return precioDiario; }
    public BigDecimal getImporteTotal() { return importeTotal; }
    public EstadoReserva getEstado() { return estado; }
}