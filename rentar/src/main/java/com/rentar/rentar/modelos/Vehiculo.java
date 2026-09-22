package com.rentar.rentar.modelos;

import java.math.BigDecimal;

import com.rentar.rentar.enums.EstadoVehiculo;
import com.rentar.rentar.enums.TipoVehiculo;
import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patente;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int anio;

    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

   @Column(nullable = false, precision = 10, scale = 2)
   private BigDecimal precioDiario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVehiculo estado;

    @Column(nullable = false)
    private boolean activo;

public Vehiculo() {
}


public Vehiculo(String patente, String marca, String modelo, int anio, String color, BigDecimal precioDiario,
        boolean activo) {
    this.patente = patente;
    this.marca = marca;
    this.modelo = modelo;
    this.anio = anio;
    this.color = color;
    this.precioDiario = precioDiario;
    this.activo = activo;
}


public Long getId() {
    return id;
}
protected void setId(Long id) {
    this.id = id;
}
public String getPatente() {
    return patente;
}
public void setPatente(String patente) {
    this.patente = patente;
}
public String getMarca() {
    return marca;
}
public void setMarca(String marca) {
    this.marca = marca;
}
public String getModelo() {
    return modelo;
}
public void setModelo(String modelo) {
    this.modelo = modelo;
}
public int getAnio() {
    return anio;
}
public void setAnio(int anio) {
    this.anio = anio;
}
public String getColor() {
    return color;
}
public void setColor(String color) {
    this.color = color;
}
public BigDecimal getPrecioDiario() {
    return precioDiario;
}
public void setPrecioDiario(BigDecimal precioDiario) {
    this.precioDiario = precioDiario;
}
public boolean isActivo() {
    return activo;
}
public void setActivo(boolean activo) {
    this.activo = activo;
}


@Override
public String toString() {
    return "Vehiculo [id=" + id + ", patente=" + patente + ", marca=" + marca + ", modelo=" + modelo + ", anio=" + anio
            + ", color=" + color + ", precioDiario=" + precioDiario + ", activo=" + activo + "]";
}


}
