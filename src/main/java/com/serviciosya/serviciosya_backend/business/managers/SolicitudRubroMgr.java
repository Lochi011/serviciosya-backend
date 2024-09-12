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
import java.util.Map;

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
     * @param cedula  Cédula del usuario ofertante.
     * @param rubroId ID del rubro al que se solicita la habilitación.
     * @return La solicitud creada.
     * @throws EntidadNoExiste Si el usuario o rubro no existen.
     */
    public void crearSolicitudRubro(Long cedulaOfertante, String nombreRubro) throws EntidadNoExiste, InvalidInformation {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findByCedulaWithRubros(cedulaOfertante)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));


        Rubro rubro = rubroRepository.findOneByNombre(nombreRubro)
                .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado."));

        if (ofertante.getRubros().contains(rubro)) {
            throw new InvalidInformation("El ofertante ya tiene habilitado este rubro.");
        }

        SolicitudRubro solicitud = SolicitudRubro.builder()
                .usuarioOfertante(ofertante)
                .rubro(rubro)
                .estado(SolicitudRubro.EstadoSolicitud.PENDIENTE)
                .fechaCreacion(new Date())
                .build();

        solicitudRubroRepository.save(solicitud);
        System.out.println("Solicitud creada con ID: " + solicitud.getId() +"del ofertante: " + ofertante.getCedula() + " al rubro: " + rubro.getId());
    }

    /**
     * Aprueba una solicitud de habilitación de rubro.
     *
     * @param solicitudId ID de la solicitud a aprobar.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void aprobarSolicitud(Long solicitudId) throws EntidadNoExiste {
        SolicitudRubro solicitud = solicitudRubroRepository.findByIdWithUsuarioOfertanteAndRubro(solicitudId)
                .orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));

        UsuarioOfertante usuarioOfertante = solicitud.getUsuarioOfertante();

        UsuarioOfertante usuarioOfertanteConRubros = usuarioOfertanteRepository.findByCedulaWithRubros(usuarioOfertante.getCedula())
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));

        usuarioOfertanteConRubros.getRubros().add(solicitud.getRubro());

        solicitud.setEstado(SolicitudRubro.EstadoSolicitud.APROBADA);
        solicitud.setFechaResolucion(new Date());



        solicitudRubroRepository.save(solicitud);
        System.out.println("Solicitud aprobada con ID: " + solicitud.getId() +"del ofertante: " + usuarioOfertante.getCedula() + " al rubro: " + solicitud.getRubro().getId());
    }

    /**
     * Rechaza una solicitud de habilitación de rubro.
     *
     * @param solicitudId     ID de la solicitud a rechazar.
     * @param comentarioAdmin Comentario del administrador explicando el rechazo.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void rechazarSolicitud(Long solicitudId) throws EntidadNoExiste {
        SolicitudRubro solicitud = solicitudRubroRepository.findById(solicitudId)
                .orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));

        solicitud.setEstado(SolicitudRubro.EstadoSolicitud.RECHAZADA);

        solicitud.setFechaResolucion(new Date());

        solicitudRubroRepository.save(solicitud);
        System.out.println("Solicitud rechazada con ID: " + solicitud.getId() );
    }

    /**
     * Obtiene todas las solicitudes de habilitación de rubros pendientes.
     *
     * @return Lista de solicitudes pendientes.
     */
    public List<SolicitudRubro> obtenerSolicitudesPendientes() {
        return solicitudRubroRepository.findAllByEstado(SolicitudRubro.EstadoSolicitud.PENDIENTE);
    }

    /**
     * Obtiene todas las solicitudes de habilitación de rubros de un ofertante específico.
     *
     * @param cedula Cédula del ofertante.
     * @return Lista de solicitudes del ofertante.
     * @throws EntidadNoExiste Si el ofertante no existe.
     */
    public List<SolicitudRubro> obtenerSolicitudesPorOfertante(Long cedula) throws EntidadNoExiste {
        return solicitudRubroRepository.findAllByUsuarioOfertanteCedula(cedula)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado."));
    }


}
