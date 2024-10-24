package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
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
}
