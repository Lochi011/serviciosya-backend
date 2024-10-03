package com.serviciosya.serviciosya_backend.business.entities.dto;

public class ContratacionDTO {

    private String nombreServicio;
    private int precioServicio;
    private String nombreOfertante;
    private String apellidoOfertante;

    // Constructor
    public ContratacionDTO(String nombreServicio, int precioServicio, String nombreOfertante, String apellidoOfertante) {
        this.nombreServicio = nombreServicio;
        this.precioServicio = precioServicio;
        this.nombreOfertante = nombreOfertante;
        this.apellidoOfertante = apellidoOfertante;
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
}
