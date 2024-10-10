package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.dto.ContratacionDetallesDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.ContratacionResumenDTO;

public class ContratacionMapper {
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

}
