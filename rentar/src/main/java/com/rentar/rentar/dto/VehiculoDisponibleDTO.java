package com.rentar.rentar.dto;

import com.rentar.rentar.enums.TipoVehiculo;

import java.math.BigDecimal;

public class VehiculoDisponibleDTO {

    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private TipoVehiculo tipo;
    private BigDecimal precioDiario;

    public VehiculoDisponibleDTO(
            String patente,
            String marca,
            String modelo,
            int anio,
            String color,
            TipoVehiculo tipo,
            BigDecimal precioDiario) {

        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.tipo = tipo;
        this.precioDiario = precioDiario;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public String getColor() {
        return color;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public BigDecimal getPrecioDiario() {
        return precioDiario;
    }
}