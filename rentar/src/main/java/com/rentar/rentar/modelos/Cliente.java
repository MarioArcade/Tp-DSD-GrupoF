package com.rentar.rentar.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private boolean activo;

    public Cliente() {
    }


    public Cliente(String documento, String nombre, String apellido, String email, String telefono,
            LocalDate fechaNacimiento, boolean activo) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
    }


    public Long getId() {
        return id;
    }


    protected void setId(Long id) {
        this.id = id;
    }


    public String getDocumento() {
        return documento;
    }


    public void setDocumento(String documento) {
        this.documento = documento;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }


    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public boolean isActivo() {
        return activo;
    }


    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    @Override
    public String toString() {
        return "Cliente [id=" + id + ", documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido
                + ", email=" + email + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + ", activo="
                + activo + "]";
    }
    


}
