package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.SolicitudRubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SolicitudRubroMgr {

    @Autowired
    private SolicitudRubroRepository solicitudRubroRepository;

    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private RubroRepository rubroRepository;

    /**
     * Crea una nueva solicitud de habilitación de rubro.
     *
     * @param cedula    Cédula del usuario ofertante.
     * @param rubroId   ID del rubro al que se solicita la habilitación.
     * @param motivo    Motivo de la solicitud.
     * @return La solicitud creada.
     * @throws EntidadNoExiste Si el usuario o rubro no existen.
     */
    public SolicitudRubro crearSolicitud(Long cedula, Long rubroId, String motivo) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByCedula(cedula)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));

        Rubro rubro = rubroRepository.findById(rubroId)
                .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado."));

        SolicitudRubro solicitud = SolicitudRubro.builder()
                .usuarioOfertante(ofertante)
                .rubro(rubro)
                .estado(SolicitudRubro.EstadoSolicitud.PENDIENTE)
                .fechaCreacion(new Date())
                .motivo(motivo)
                .build();

        return solicitudRubroRepository.save(solicitud);
    }

    /**
     * Aprueba una solicitud de habilitación de rubro.
     *
     * @param solicitudId ID de la solicitud a aprobar.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void aprobarSolicitud(Long solicitudId) throws EntidadNoExiste {
        SolicitudRubro solicitud = solicitudRubroRepository.findById(solicitudId)
                .orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));

        solicitud.setEstado(SolicitudRubro.EstadoSolicitud.APROBADA);
        solicitud.setFechaResolucion(new Date());

        solicitudRubroRepository.save(solicitud);
    }

    /**
     * Rechaza una solicitud de habilitación de rubro.
     *
     * @param solicitudId      ID de la solicitud a rechazar.
     * @param comentarioAdmin  Comentario del administrador explicando el rechazo.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void rechazarSolicitud(Long solicitudId, String comentarioAdmin) throws EntidadNoExiste {
        SolicitudRubro solicitud = solicitudRubroRepository.findById(solicitudId)
                .orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));

        solicitud.setEstado(SolicitudRubro.EstadoSolicitud.RECHAZADA);
        solicitud.setComentarioAdmin(comentarioAdmin);
        solicitud.setFechaResolucion(new Date());

        solicitudRubroRepository.save(solicitud);
    }

    /**
     * Obtiene todas las solicitudes de habilitación de rubros pendientes.
     *
     * @return Lista de solicitudes pendientes.
     */
    public List<SolicitudRubro> obtenerSolicitudesPendientes() {
        return solicitudRubroRepository.findByEstado(SolicitudRubro.EstadoSolicitud.PENDIENTE);
    }

    /**
     * Obtiene todas las solicitudes de habilitación de rubros de un ofertante específico.
     *
     * @param cedula Cédula del ofertante.
     * @return Lista de solicitudes del ofertante.
     * @throws EntidadNoExiste Si el ofertante no existe.
     */
    public List<SolicitudRubro> obtenerSolicitudesPorOfertante(Long cedula) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findByCedula(cedula)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));

        return solicitudRubroRepository.findByUsuarioOfertante(ofertante);
    }
}
