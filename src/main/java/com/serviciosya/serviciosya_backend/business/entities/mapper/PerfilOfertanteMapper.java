package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.entities.dto.PerfilOfertanteDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;

import java.util.List;
import java.util.stream.Collectors;

public class PerfilOfertanteMapper {

    public static PerfilOfertanteDTO toDto(UsuarioOfertante usuarioOfertante) {
        if (usuarioOfertante == null) {
            return null;
        }

       List<ServicioDto> servicios = usuarioOfertante.getServicios().stream().map(ServicioMapper::toDto).collect(Collectors.toList());

       List<String>  rubros = usuarioOfertante.getRubros().stream().map(rubro -> rubro.getNombre()).collect(Collectors.toList());

       return PerfilOfertanteDTO.builder().

                id(usuarioOfertante.getId()).
                nombre(usuarioOfertante.getNombre()).
                apellido(usuarioOfertante.getApellido()).
                direccion(usuarioOfertante.getDireccion()).
                genero(usuarioOfertante.getGenero()).
                fechaNacimiento(usuarioOfertante.getFechaNacimiento()).
                servicios(servicios).
                rubros(rubros).

               build();
    }
}
