package com.serviciosya.serviciosya_backend.business.entities.mapper;


import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;

public class ServicioMapper {

    public static ServicioDto toDto(Servicio servicio) {
        if (servicio == null) {
            return null;
        }

        return new ServicioDto(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getPrecio(),
                servicio.getPuntuacion(),
                servicio.getDescripcion(),
                servicio.getHoraDesde(),
                servicio.getHoraHasta(),
                servicio.getDuracionServicio(),
                servicio.getEtiquetas(),
                servicio.getUsuarioOfertante() != null ? servicio.getUsuarioOfertante().getId() : null,
                servicio.getRubro() != null ? servicio.getRubro().getNombre() : null,
                servicio.getDiasSeleccionados()
        );
    }
}
