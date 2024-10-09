package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
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
                contratacion.getEstado().toString()
        );
    }
}
