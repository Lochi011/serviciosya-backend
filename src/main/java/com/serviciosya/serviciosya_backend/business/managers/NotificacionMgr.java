package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.NotificacionRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotificacionMgr {


    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired

    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    public List<Notificacion> obtenerNotificacionesNoLeidasPorUsuarioOferanteId(String email) throws Exception {

        UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario ofertante no encontrado con email: " + email));

        return notificacionRepository.findByUsuarioOfertanteAndLeidoFalse(usuarioOfertante).orElseThrow(() -> new EntidadNoExiste("NO_UNREAD_NOTIFICATIONS","No se encontraron notificaciones no leidas para el usuario ofertante con email: " + email));
    }
}
