package com.serviciosya.serviciosya_backend.business.entities.dto;

import java.time.LocalDate;

public class ContratacionDTO {

    private Long id_contratacion;
    private String nombreServicio;
    private int precioServicio;
    private String nombreOfertante;
    private String apellidoOfertante;
    private LocalDate fechaContratacion;

    private String hora;

    private String estado;

    private Boolean isFavorite;

    private Float puntuacion;


    // Constructor
    public ContratacionDTO(Long id, String nombreServicio, int precioServicio, String nombreOfertante, String apellidoOfertante, LocalDate fechaContratacion, String hora, String estado, Boolean isFavorite, Float puntuacion) {
        this.id_contratacion = id;
        this.nombreServicio = nombreServicio;
        this.precioServicio = precioServicio;
        this.nombreOfertante = nombreOfertante;
        this.apellidoOfertante = apellidoOfertante;
        this.fechaContratacion = fechaContratacion;
        this.hora = hora;
        this.estado = estado;
        this.isFavorite = isFavorite;
        this.puntuacion = puntuacion;
    }

    // Getters y Setters


    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Long getId_contratacion() {
        return id_contratacion;
    }

    public void setId_contratacion(Long id_contratacion) {
        this.id_contratacion = id_contratacion;
    }

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
