package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Reseña;
import com.serviciosya.serviciosya_backend.business.entities.dto.ReseñaDto;

public class ReseñaMapper {

    public static ReseñaDto toDto(Reseña reseña) {
        if (reseña == null) {
            return null;
        }

        return ReseñaDto.builder()
                .id(reseña.getId())
                .comentario(reseña.getComentario())
                .puntuacion(reseña.getPuntuacion())
                .fechaPublicacion(reseña.getFechaPublicacion().toString())
                .nombreDemandante(reseña.getUsuarioDemandante().getNombre())
                .apellidoDemandante(reseña.getUsuarioDemandante().getApellido())
                .build();
    }
}
