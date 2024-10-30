package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.controllers.SolicitudRubroController;
import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.SolicitudRubroRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudRubroMgr {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudRubroController.class);

    @Autowired
    private SolicitudRubroRepository solicitudRubroRepository;

    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private RubroRepository rubroRepository;

    /**
     * Crea una nueva solicitud de habilitación de rubro.
     *
     * @param cedulaOfertante  Cédula del usuario ofertante.
     * @param nombreRubro ID del rubro al que se solicita la habilitación.
     * @return La solicitud creada.
     * @throws EntidadNoExiste Si el usuario o rubro no existen.
     */
    public void crearSolicitudRubro(Long cedulaOfertante, String nombreRubro) throws EntidadNoExiste, InvalidInformation {

        logger.info("Nombre del rubro recibido: '{}'", nombreRubro);

        // Validar parámetros de entrada
        if (cedulaOfertante == null || nombreRubro == null || nombreRubro.trim().isEmpty()) {
            throw new InvalidInformation("Cédula del ofertante y nombre del rubro no pueden ser nulos o vacíos.");
        }

        // Buscar el ofertante por su cédula
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByCedula(cedulaOfertante)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con la cédula: " + cedulaOfertante));
        System.out.println(nombreRubro);
        // Buscar el rubro por su nombre
        Rubro rubro = rubroRepository.findOneByNombre(nombreRubro.trim())
                .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado con el nombre: " + nombreRubro));

        // Verificar si el ofertante ya tiene el rubro habilitado
        if (ofertante.getRubros().contains(rubro)) {
            throw new InvalidInformation("El ofertante ya tiene habilitado el rubro: " + nombreRubro);
        }

        // Verificar si ya existe una solicitud pendiente para este rubro
        Optional<SolicitudRubro> solicitudExistente = solicitudRubroRepository.findByUsuarioOfertanteAndRubro(ofertante, rubro);
        if (solicitudExistente.isPresent()) {
            if (solicitudExistente.get().getEstado() == SolicitudRubro.EstadoSolicitud.PENDIENTE) {
                throw new InvalidInformation("Ya existe una solicitud pendiente para el rubro: " + nombreRubro);
            }
            else if (solicitudExistente.get().getEstado() == SolicitudRubro.EstadoSolicitud.APROBADA) {
                throw new InvalidInformation("El ofertante ya tiene habilitado el rubro: " + nombreRubro);
            }
            else if (solicitudExistente.get().getEstado() == SolicitudRubro.EstadoSolicitud.RECHAZADA) {
                throw new InvalidInformation("Usted ya ha sido rechazado para publicar servicios en el rubro: " + nombreRubro);
            } else if (solicitudExistente.get().getEstado() == SolicitudRubro.EstadoSolicitud.RECHAZADA_EXP) {
                solicitudExistente.get().setEstado(SolicitudRubro.EstadoSolicitud.PENDIENTE);
            }

        }

        // Crear la nueva solicitud de rubro
        SolicitudRubro solicitud = SolicitudRubro.builder()
                .usuarioOfertante(ofertante)
                .rubro(rubro)
                .estado(SolicitudRubro.EstadoSolicitud.PENDIENTE)
                .fechaCreacion(new Date())
                .build();

        try {
            solicitudRubroRepository.save(solicitud);
            System.out.println("Solicitud creada con ID: " + solicitud.getId() + ", ofertante: " + ofertante.getCedula() + ", rubro: " + rubro.getNombre());
        } catch (Exception e) {
            // Manejar errores de persistencia o inesperados
            System.err.println("Error al guardar la solicitud: " + e.getMessage());
            throw new RuntimeException("Error interno al crear la solicitud de rubro.");
        }
    }


    /**
     * Aprueba una solicitud de habilitación de rubro.
     *
     * @param solicitudId ID de la solicitud a aprobar.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void aprobarSolicitud(Long solicitudId) throws EntidadNoExiste {
        SolicitudRubro solicitud = solicitudRubroRepository.findByIdWithUsuarioOfertanteAndRubro(solicitudId).orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));

        UsuarioOfertante usuarioOfertante = solicitud.getUsuarioOfertante();

        Rubro rubro  = solicitud.getRubro();
        usuarioOfertante.agregarRubro(rubro);
        solicitud.setEstado(SolicitudRubro.EstadoSolicitud.APROBADA);
        solicitud.setFechaResolucion(new Date());



        solicitudRubroRepository.save(solicitud);
        usuarioOfertanteRepository.save(usuarioOfertante);

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

    @Scheduled(cron = "0 0 0 * * ?") // Ejecución diaria a medianoche
    public void actualizarSolicitudesExpiradas() {
        List<SolicitudRubro> solicitudesRechazadas = solicitudRubroRepository.findAllByEstado(SolicitudRubro.EstadoSolicitud.RECHAZADA);


        solicitudesRechazadas.forEach(solicitud -> {
            Date fechaResolucion = solicitud.getFechaResolucion();
            Date fechaRehabilitacion = new Date(fechaResolucion.getTime() + 3 * 24 * 60 * 60 * 1000); // 3 días en milisegundos

            if (fechaRehabilitacion.before(new Date())) {
                solicitud.setEstado(SolicitudRubro.EstadoSolicitud.RECHAZADA_EXP);
                solicitudRubroRepository.save(solicitud);
            }

        });
    }

    public List<String> obtenerNombresRubrosPorOfertante(Long ofertanteId) throws EntidadNoExiste {
        // Fetch the UsuarioOfertante using the ID
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findById(ofertanteId)
                .orElseThrow(() -> new EntidadNoExiste("El ofertante no existe"));

        // Map rubros to their names and collect as a List<String>
        return ofertante.getRubros().stream()
                .map(Rubro::getNombre)
                .collect(Collectors.toList());
    }



}
