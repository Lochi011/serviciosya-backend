package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.dto.*;

public class ContratacionMapper {

    public static ContratacionTerminadasDTO toDto (Contratacion contratacion){
        if (contratacion == null){
            return null;
        }

        return ContratacionTerminadasDTO.builder().
                id_contratacion(contratacion.getId()).
                nombreServicio(contratacion.getServicio().getNombre()).
                precioServicio(contratacion.getServicio().getPrecio()).
                nombreOfertante(contratacion.getOfertante().getNombre()).
                apellidoOfertante(contratacion.getOfertante().getApellido()).
                fechaContratacion(contratacion.getFechaContratacion()).
                hora(contratacion.getHora()).
                estado(String.valueOf(contratacion.getEstado())).
                isFavorite(contratacion.getIsFavorite()).
                puntuacion(contratacion.getPuntuacion()).
                nombreRubro(contratacion.getServicio().getRubro().getNombre()).build();



    }
    public static ContratacionResumenDTO toResumenDto(Contratacion contratacion) {
        if (contratacion == null) {
            return null;
        }

        return new ContratacionResumenDTO(
                contratacion.getId(),
                contratacion.getDemandante().getNombre(),
                contratacion.getDemandante().getApellido(),
                contratacion.getServicio().getNombre(),
                contratacion.getFechaContratacion(),
                contratacion.getHora(),
                contratacion.getEstado().toString(),
                contratacion.getServicio().getRubro().getNombre()

        );
    }

    public static ContratacionDetallesDTO convertirADetallesDTO(Contratacion contratacion) {


        return ContratacionDetallesDTO.builder()
                .id(contratacion.getId())
                .nombreDemandante(contratacion.getDemandante().getNombre())
                .apellidoDemandante(contratacion.getDemandante().getApellido())
                .emailDemandante(contratacion.getDemandante().getEmail())
                .direccion(contratacion.getDireccion())
                .apartamento(contratacion.getApartamento())
                .fechaContratacion(contratacion.getFechaContratacion())
                .hora(contratacion.getHora())
                .comentario(contratacion.getComentario())
                .nombreServicio(contratacion.getServicio().getNombre())
                .precioServicio(contratacion.getServicio().getPrecio())
                .estado(contratacion.getEstado().toString())
                .nombreRubro(contratacion.getServicio().getRubro().getNombre())
                .build();
    }

    public static ContratacionDetalles2DTO convertirADetalles2DTO(Contratacion contratacion) {
        return ContratacionDetalles2DTO.builder()
                .id(contratacion.getId())
                .nombreOfertante(contratacion.getOfertante().getNombre())
                .apellidoOfertante(contratacion.getOfertante().getApellido())
                .emailOfertante(contratacion.getOfertante().getEmail())
                .direccion(contratacion.getOfertante().getDireccion())
                .apartamento(contratacion.getApartamento())
                .fechaContratacion(contratacion.getFechaContratacion())
                .hora(contratacion.getHora())
                .comentario(contratacion.getComentario())
                .nombreServicio(contratacion.getServicio().getNombre())
                .precioServicio(contratacion.getServicio().getPrecio())
                .estado(contratacion.getEstado().toString())
                .nombreRubro(contratacion.getServicio().getRubro().getNombre())
                .justificacionRechazo(contratacion.getJustificacionRechazo())
                .respuestaOfertante(contratacion.getRespuestaOfertante() != null ? contratacion.getRespuestaOfertante().getMensaje() : null)
                .emailOfertanteRespuesta(contratacion.getRespuestaOfertante() != null ? contratacion.getRespuestaOfertante().getEmail() : null)
                .telefonoOfertanteRespuesta(contratacion.getRespuestaOfertante() != null ? contratacion.getRespuestaOfertante().getTelefono() : null)
                .build();
    }

    public static ContratacionResumenDemandanteDTO toResumenDemandanteDTO(Contratacion contratacion){

        ContratacionResumenDemandanteDTO contratacionResumenDemandanteDTO = ContratacionResumenDemandanteDTO.builder().
                id(contratacion.getId()).
                nombreOfertante(contratacion.getOfertante().getNombre()).
                apellidoOfertante(contratacion.getOfertante().getApellido()).
                nombreServicio(contratacion.getServicio().getNombre()).
                fechaContratacion(contratacion.getFechaContratacion()).
                hora(contratacion.getHora()).
                estado(contratacion.getEstado().toString()).
                nombreRubro(contratacion.getServicio().getRubro().getNombre()).
                build();

        if (contratacionResumenDemandanteDTO.getEstado() == "RECHAZADA"){
            contratacionResumenDemandanteDTO.setJustificacionRechazo(contratacion.getJustificacionRechazo());
        }

        return contratacionResumenDemandanteDTO;

    }

}
