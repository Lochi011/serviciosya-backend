package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
import com.serviciosya.serviciosya_backend.business.entities.NotificacionDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDemandanteDTO;
import com.serviciosya.serviciosya_backend.business.entities.mapper.NotificacionMapper;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.NotificacionDemandanteRepository;
import com.serviciosya.serviciosya_backend.persistance.NotificacionRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioDemandanteRespository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class NotificacionMgr {


    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired

    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private UsuarioDemandanteRespository usuarioDemandanteRepository;
    @Autowired
    private NotificacionDemandanteRepository notificacionDemandanteRepository;

    public List<Notificacion> obtenerNotificacionesNoLeidasPorUsuarioOferanteId(String email) throws Exception {

        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario ofertante no encontrado con email: " + email));

        return notificacionRepository.findByUsuarioOfertanteAndLeidoFalse(usuarioOfertante).orElseThrow(() -> new EntidadNoExiste("NO_UNREAD_NOTIFICATIONS","No se encontraron notificaciones no leidas para el usuario ofertante con email: " + email));
    }

    public void marcarNotificacionComoLeida(Long idNotificacion) throws Exception {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion).orElseThrow(() -> new EntidadNoExiste("Notificación no encontrada con id: " + idNotificacion));
        notificacion.setLeido(true);
        notificacionRepository.save(notificacion);
    }

    public  List<NotificacionDTO> obtenerNotificacionesDTONoLeidasPorUsuarioOferanteEmail(String email) throws Exception {
        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario ofertante no encontrado con email: " + email));
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioOfertanteAndLeidoFalse(usuarioOfertante).orElseThrow(() -> new EntidadNoExiste("NO_UNREAD_NOTIFICATIONS","No se encontraron notificaciones no leidas para el usuario ofertante con email: " + email));
        return notificaciones.stream().
                map(NotificacionMapper::toDto).
                collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNotificacionesDTOPorUsuarioOferanteEmail(String email) throws EntidadNoExiste {
        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario ofertante no encontrado con email: " + email));
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioOfertante(usuarioOfertante).orElseThrow(() -> new EntidadNoExiste("NO_NOTIFICATIONS","No se encontraron notificaciones para el usuario ofertante con email: " + email));
        return notificaciones.stream().
                map(NotificacionMapper::toDto).
                collect(Collectors.toList());
    }

    public Long contarNotificacionesNoLeidasPorUsuarioOferanteEmail(String email) throws EntidadNoExiste {
        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario ofertante no encontrado con email: " + email));
        return notificacionRepository.countByUsuarioOfertanteAndLeidoFalse(usuarioOfertante);
    }

    public List<NotificacionDemandanteDTO> obtenerNotificacionesDTONoLeidasPorUsuarioDemandanteEmail(String email) throws EntidadNoExiste {
        UsuarioDemandante usuarioDemandante = usuarioDemandanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario demandante no encontrado con email: " + email));
        List<NotificacionDemandante> notificaciones = notificacionDemandanteRepository.findByUsuarioDemandanteAndLeidoFalse(usuarioDemandante).orElseThrow(() -> new EntidadNoExiste("NO_UNREAD_NOTIFICATIONS","No se encontraron notificaciones no leidas para el usuario demandante con email: " + email));
        return notificaciones.stream().
                map(NotificacionMapper::toDtoDemandante).
                collect(Collectors.toList());
    }
}
