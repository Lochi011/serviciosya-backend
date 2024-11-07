package com.serviciosya.serviciosya_backend.business.entities.mapper;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.entities.dto.PerfilOfertanteDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.ReseñaDto;
import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.managers.ContratacionMgr;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PerfilOfertanteMapper {

    @Autowired
    private  ServicioMgr servicioMgr;
    @Autowired
    private ContratacionMgr contratacionMgr;

    @Autowired
    private SolicitudRubroMgr solicitudRubroMgr;



    public  PerfilOfertanteDTO toDto(UsuarioOfertante usuarioOfertante) {
        if (usuarioOfertante == null) {
            return null;
        }

        List<ReseñaDto> resenas = usuarioOfertante.getReseñas().stream().map(ReseñaMapper::toDto).collect(Collectors.toList());

       List<ServicioDto> servicios = usuarioOfertante.getServicios().stream().map(ServicioMapper::toDto).collect(Collectors.toList());

        List<String>  rubros = null;
        try {
            rubros = solicitudRubroMgr.obtenerNombresRubrosPorOfertante(usuarioOfertante.getId());
        } catch (EntidadNoExiste e) {
            throw new RuntimeException(e);
        }


        try {
            return PerfilOfertanteDTO.builder().

                     id(usuarioOfertante.getId()).
                     nombre(usuarioOfertante.getNombre()).
                     apellido(usuarioOfertante.getApellido()).
                     direccion(usuarioOfertante.getDireccion()).
                     genero(usuarioOfertante.getGenero()).
                     fechaNacimiento(usuarioOfertante.getFechaNacimiento()).
                     servicios(servicios).
                     rubros(rubros).
                    fotoPerfil(usuarioOfertante.getFotoPerfil()).
                    cantidadContratacionesTerminadas(contratacionMgr.obtenerCantidadContratacionesTerminadasPorOfertante(usuarioOfertante.getId())).
                    cantidadServicios(servicioMgr.obtenerCantidadServiciosPorOfertante(usuarioOfertante.getId())).
                    descripcion(usuarioOfertante.getDescripcion()).

                    build();
        } catch (EntidadNoExiste e) {
            throw new RuntimeException(e);
        }
    }
}
