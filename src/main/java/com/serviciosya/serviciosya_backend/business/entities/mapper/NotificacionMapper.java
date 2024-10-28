package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
import com.serviciosya.serviciosya_backend.business.entities.NotificacionDemandante;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDemandanteDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDTO;

public class NotificacionMapper {

    public static NotificacionDTO toDto(Notificacion notificacion) {
        if (notificacion == null) {
            return null;
        }

        return new NotificacionDTO(
                notificacion.getId(),
                notificacion.getMensaje(),
                notificacion.getFechaCreacion(),
                notificacion.isLeido(),
                notificacion.getUsuarioOfertante() != null ? notificacion.getUsuarioOfertante().getId() : null,
                notificacion.getContratacion() != null ? notificacion.getContratacion().getId() : null,
                notificacion.getContratacion() != null ? notificacion.getContratacion().getServicio().getNombre() : null,
                notificacion.getContratacion() != null ? notificacion.getContratacion().getDemandante().getNombre() : null,
                notificacion.getContratacion() != null ? notificacion.getContratacion().getDemandante().getApellido() : null
        );
    }

    public static NotificacionDemandanteDTO toDtoDemandante(NotificacionDemandante notificacion) {
        if (notificacion == null) {
            return null;
        }

        return NotificacionDemandanteDTO.builder()
                .id(notificacion.getId())
                .mensaje(notificacion.getMensaje())
                .fechaCreacion(notificacion.getFechaCreacion())
                .leido(notificacion.isLeido())
                .usuarioDemandanteId(notificacion.getUsuarioDemandante() != null ? notificacion.getUsuarioDemandante().getId() : null)
                .contratacionId(notificacion.getContratacion() != null ? notificacion.getContratacion().getId() : null)
                .nombreServicio(notificacion.getContratacion() != null ? notificacion.getContratacion().getServicio().getNombre() : null)
                .nombreOfertante(notificacion.getContratacion() != null ? notificacion.getContratacion().getOfertante().getNombre() : null)
                .apellidoOfertante(notificacion.getContratacion() != null ? notificacion.getContratacion().getOfertante().getApellido() : null)
                .build();
    }
}
