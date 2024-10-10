package com.serviciosya.serviciosya_backend.business.entities.dto;

import java.time.LocalDate;

public class ContratacionDTO {

    private String nombreServicio;
    private int precioServicio;
    private String nombreOfertante;
    private String apellidoOfertante;
    private LocalDate fechaContratacion;
    private String hora;
    private String estado;


    // Constructor
    public ContratacionDTO(String nombreServicio, int precioServicio, String nombreOfertante, String apellidoOfertante, LocalDate fechaContratacion, String hora, String estado) {
        this.nombreServicio = nombreServicio;
        this.precioServicio = precioServicio;
        this.nombreOfertante = nombreOfertante;
        this.apellidoOfertante = apellidoOfertante;
        this.fechaContratacion = fechaContratacion;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y Setters
    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public int getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(int precioServicio) {
        this.precioServicio = precioServicio;
    }

    public String getNombreOfertante() {
        return nombreOfertante;
    }

    public void setNombreOfertante(String nombreOfertante) {
        this.nombreOfertante = nombreOfertante;
    }

    public String getApellidoOfertante() {
        return apellidoOfertante;
    }

    public void setApellidoOfertante(String apellidoOfertante) {
        this.apellidoOfertante = apellidoOfertante;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
