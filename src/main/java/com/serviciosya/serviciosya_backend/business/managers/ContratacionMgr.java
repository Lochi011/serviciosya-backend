package com.serviciosya.serviciosya_backend.business.managers;


import com.serviciosya.serviciosya_backend.business.entities.*;
import com.serviciosya.serviciosya_backend.business.entities.dto.*;
import com.serviciosya.serviciosya_backend.business.entities.mapper.ContratacionMapper;
import com.serviciosya.serviciosya_backend.business.entities.mapper.NotificacionMapper;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


import static com.serviciosya.serviciosya_backend.business.entities.Contratacion.EstadoContratacion.PENDIENTE;
@AllArgsConstructor
@NoArgsConstructor

@Service
public class ContratacionMgr {
    @Autowired
    private ContratacionRepository contratacionRepository;
    @Autowired
    private UsuarioDemandanteRespository usuarioDemandanteRepository;
    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;
    @Autowired
    private RubroRepository rubroRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private NotificacionDemandanteRepository notificacionDemandanteRepository;
    @Autowired
    private RespuestaOfertanteRepository respuestaOfertanteRepository;


    private static final Logger logger = LoggerFactory.getLogger(ServicioMgr.class);

//    public void crearContratacion(Long idDemandante, Long idOfertante, LocalDate fechaServicio, String direccion, String apartamento, String hora, String comentario, Long idServicio) throws EntidadNoExiste, InvalidInformation {
//        try {
//            if (fechaServicio == null || direccion == null || hora == null || idDemandante == null || idDemandante == null || idServicio == null) {
//                throw new InvalidInformation("Faltan datos obligatorios para la realizacion de la contratacion.");
//
//            }
//
//        } catch (InvalidInformation e) {
//            throw new RuntimeException(e);
//        }
//        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneById(Long.valueOf(idOfertante)).orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con id: " + idOfertante));
//        UsuarioDemandante demandante = usuarioDemandanteRepository.findOneById(Long.valueOf(idDemandante)).orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con id: " + idDemandante));
//        Servicio servicio = servicioRepository.findOneById(Long.valueOf(idServicio)).orElseThrow(() -> new EntidadNoExiste("Servicio no enccontrado con id  " + idServicio));
//
//        //verificar que no exista contratacion pedniente
//        Optional<Contratacion> contratacionExistente = contratacionRepository.findByDemandanteAndOfertanteAndServicioAndFechaContratacionAndEstado(demandante, ofertante, servicio, fechaServicio, PENDIENTE);
//        if (contratacionExistente.isPresent()) {
//            if (contratacionExistente.get().getEstado() == PENDIENTE) {
//                throw new InvalidInformation("Ya existe una solicitud pendiente para una contratacion ese dia");
//            } else if (contratacionExistente.isPresent()) {
//                if (contratacionExistente.get().getEstado() == Contratacion.EstadoContratacion.ACEPTADA) {
//                    throw new InvalidInformation("Ya existe una contratacion solicitada para ese dia");
//                }
//
//            }
//        }
//
//        Contratacion contratacion = Contratacion.builder()
//                .demandante(demandante)
//                .ofertante(ofertante)
//                .servicio(servicio)
//                .fechaContratacion(fechaServicio)
//                .direccion(direccion)
//                .hora(hora)
//                .comentario(comentario)
//                .estado(PENDIENTE)
//                .apartamento(apartamento)
//                .build();
//        try {
//            contratacionRepository.save(contratacion);
//            System.out.println("Contratacion creada con ID: " + contratacion.getId() + ", ofertante: " + ofertante.getCedula() + ", servicio: " + servicio.getNombre());
//        } catch (Exception e) {
//            // Manejar errores de persistencia o inesperados
//            System.err.println("Error al guardar la contratacion: " + e.getMessage());
//            throw new RuntimeException("Error interno al crear la contratacion del servicio.");
//        }
//
//        Notificacion notificacion = Notificacion.builder()
//                .usuarioOfertante(ofertante)
//                .contratacion(contratacion)
//                .mensaje("Tienes una nueva solicitud de contratacion")
//                .fechaCreacion(LocalDateTime.now())
//                .leido(false)
//                .build();
//        try { // Guardar la notificacion
//            notificacionRepository.save(notificacion);
//            System.out.println("Notificacion creada con ID: " + notificacion.getId() + ", ofertante: " + ofertante.getCedula() + ", contratacion: " + contratacion.getId());
//        } catch (Exception e) {
//            // Manejar errores de persistencia o inesperados
//            System.err.println("Error al guardar la notificacion: " + e.getMessage());
//            throw new RuntimeException("Error interno al crear la notificacion de la contratacion.");
//        }
//
//
//    }

    public  void crearContratacion(Long idDemandante, Long idOfertante, LocalDate fechaServicio, String direccion, String apartamento, String hora, String comentario, Long idServicio) throws EntidadNoExiste, InvalidInformation {
        try {
            if (fechaServicio == null || direccion == null || hora == null || idDemandante == null || idOfertante == null || idServicio == null) {
                throw new InvalidInformation("Faltan datos obligatorios para la realizacion de la contratacion.");
            }
        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }

        // Add debug logs here to trace the values
        System.out.println("Fetching ofertante with id: " + idOfertante);
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneById(idOfertante)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con id: " + idOfertante));

        System.out.println("Fetching demandante with id: " + idDemandante);
        UsuarioDemandante demandante = usuarioDemandanteRepository.findOneById(idDemandante)
                .orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con id: " + idDemandante));

        System.out.println("Fetching servicio with id: " + idServicio);
        Servicio servicio = servicioRepository.findOneById(idServicio)
                .orElseThrow(() -> new EntidadNoExiste("Servicio no encontrado con id  " + idServicio));

        // Continue adding logs before any action that might fail
        System.out.println("Checking for existing contratacion...");
        Optional<Contratacion> contratacionExistente = contratacionRepository.findByDemandanteAndOfertanteAndServicioAndFechaContratacionAndEstado(demandante, ofertante, servicio, fechaServicio, PENDIENTE);
        if (contratacionExistente.isPresent()) {
            System.out.println("Existing contratacion found.");
            if (contratacionExistente.get().getEstado() == PENDIENTE) {
                System.out.println("Existing contratacion is pending.");
                throw new InvalidInformation("PENDING_CONTRACT_ALREADY_EXISTS", "Ya existe una solicitud pendiente para una contratacion ese dia");
            } else if (contratacionExistente.get().getEstado() == Contratacion.EstadoContratacion.ACEPTADA) {
                throw new InvalidInformation("ACCEPTED_CONTRACT_ALREADY_EXISTS", "Ya existe una contratacion solicitada para ese dia");
            }
        }

        System.out.println("Creating new contratacion...");

        Contratacion contratacion = Contratacion.builder()
                .demandante(demandante)
                .ofertante(ofertante)
                .servicio(servicio)
                .fechaContratacion(fechaServicio)
                .direccion(direccion)
                .hora(hora)
                .comentario(comentario)
                .estado(PENDIENTE)
                .apartamento(apartamento)
                .build();

        try {
            contratacionRepository.save(contratacion);
            System.out.println("Contratacion creada con ID: " + contratacion.getId());
        } catch (Exception e) {
            System.err.println("Error al guardar la contratacion: " + e.getMessage());
            throw new RuntimeException("Error interno al crear la contratacion del servicio.");
        }

        Notificacion notificacion = Notificacion.builder()
                .usuarioOfertante(ofertante)
                .contratacion(contratacion)
                .mensaje("Tienes una nueva solicitud de contratacion")
                .fechaCreacion(LocalDateTime.now())
                .leido(false)
                .build();

        try {
            notificacionRepository.save(notificacion);
            System.out.println("Notificacion creada con ID: " + notificacion.getId());
        } catch (Exception e) {
            System.err.println("Error al guardar la notificacion: " + e.getMessage());
            throw new RuntimeException("Error interno al crear la notificacion de la contratacion.");
        }
    }


    public List<Contratacion> obtenerContratacionesPorOfertante(String email) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con email: " + email));
        return contratacionRepository.findAllByOfertante(ofertante).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones para el ofertante con email: " + email));
    }



    @Transactional
    public void contactarContratacion(Long idContratacion, String mensaje, String telefono, String email) throws EntidadNoExiste, InvalidInformation {
        if (mensaje == null) {
            throw new InvalidInformation("No hay mensaje en respuesta");
        }
        if (telefono == null && email == null) {
            throw new InvalidInformation("No hay telefono ni email en respuesta");
        }

        Contratacion contratacion = contratacionRepository.findById(idContratacion)
                .orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));

        contratacion.setEstado(Contratacion.EstadoContratacion.CONTACTADA);

        RespuestaOfertante respuestaOfertante = RespuestaOfertante.builder()
                .mensaje(mensaje)
                .telefono(telefono)
                .email(email)
                .contratacion(contratacion)
                .build();

        respuestaOfertanteRepository.save(respuestaOfertante);
        contratacion.setRespuestaOfertante(respuestaOfertante);
        contratacionRepository.save(contratacion);

        // Re-fetch to verify the update
        Contratacion updatedContratacion = contratacionRepository.findById(idContratacion)
                .orElseThrow(() -> new EntidadNoExiste("Error verifying update: Contratacion not found"));

        if (updatedContratacion.getEstado() == Contratacion.EstadoContratacion.CONTACTADA) {
            NotificacionDemandante notificacionDemandante = NotificacionDemandante.builder()
                    .usuarioDemandante(contratacion.getDemandante())
                    .contratacion(contratacion)
                    .mensaje("El ofertante ha respondido a tu solicitud de contratacion para el servicio " + contratacion.getServicio().getNombre())
                    .leido(false)
                    .esMensaje(true)
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            notificacionDemandanteRepository.save(notificacionDemandante);
            System.out.println("Contratacion actualizada correctamente con estado CONTACTADA y notificación enviada.");
        } else {
            throw new RuntimeException("Error al actualizar el estado de la contratacion en la nube.");
        }
    }




    public void aceptarContratacion(Long idContratacion) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.ACEPTADA);
        contratacionRepository.save(contratacion);

        NotificacionDemandante notificacionDemandante = NotificacionDemandante.builder()
                .usuarioDemandante(contratacion.getDemandante())
                .contratacion(contratacion)
                .mensaje("Tu solicitud para el servicio " + contratacion.getServicio().getNombre() + " ha sido aceptada")
                .leido(false)
                .fechaCreacion(LocalDateTime.now())
                .build();

        notificacionDemandanteRepository.save(notificacionDemandante);
    }

    public void rechazarContratacion(Long idContratacion, String mensaje) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.RECHAZADA);
        contratacion.setJustificacionRechazo(mensaje);
        contratacionRepository.save(contratacion);

        UsuarioDemandante usuarioDemandante = contratacion.getDemandante();
        String nombreServicio = contratacion.getServicio().getNombre();

        NotificacionDemandante notificacionDemandante = NotificacionDemandante.builder().
                usuarioDemandante(usuarioDemandante).
                contratacion(contratacion).
                mensaje("Su solicitud para el servicio " + nombreServicio + " ha sido rechazada").
                leido(false).
                esMensaje(false).
                esRechazo(true).
                fechaCreacion(LocalDateTime.now()).

                build();

        notificacionDemandanteRepository.save(notificacionDemandante);
    }

    public void marcarPagadaContratacion(Long idContratacion) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.PAGADA);
        contratacionRepository.save(contratacion);
    }

    public void terminarContratacion(Long idContratacion) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.TERMINADA);
        contratacionRepository.save(contratacion);

        NotificacionDemandante notificacionDemandante = NotificacionDemandante.builder()
                .usuarioDemandante(contratacion.getDemandante())
                .contratacion(contratacion)
                .mensaje("La contratacion para el servicio " + contratacion.getServicio().getNombre() + " ha sido terminada")
                .leido(false)
                .fechaCreacion(LocalDateTime.now())
                .build();

        notificacionDemandanteRepository.save(notificacionDemandante);

    }

    public List<ContratacionResumenDTO> obtenerContratacionesResumenPorOfertante(String email) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con email: " + email));
        List<Contratacion> contrataciones = contratacionRepository.findAllByOfertante(ofertante).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones para el demandante con email: " + email));
        return contrataciones.stream().
                map(ContratacionMapper::toResumenDto).
                collect(Collectors.toList());
    }

    public ContratacionDetallesDTO obtenerDetallesContratacionDTO(Long id) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(id).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + id));
        return ContratacionMapper.convertirADetallesDTO(contratacion);
    }

    public ContratacionDetalles2DTO obtenerDetallesContratacion2DTO(Long id) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(id).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + id));
        System.out.println(contratacion);
        return ContratacionMapper.convertirADetalles2DTO(contratacion);
    }

    public List<ContratacionResumenDemandanteDTO> obtenerContratacionesResumenPorDemandante(String emailDemandante) throws EntidadNoExiste {
        UsuarioDemandante demandante = usuarioDemandanteRepository.findOneByEmail(emailDemandante).orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con email: " + emailDemandante));
        List<Contratacion> contrataciones = contratacionRepository.findAllByDemandante(demandante).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones para el demandante con email: " + emailDemandante));
        return contrataciones.stream().
                map(ContratacionMapper::toResumenDemandanteDTO).
                collect(Collectors.toList());
    }

    public int obtenerCantidadContratacionesTerminadasPorOfertante(Long id) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneById(id).orElseThrow(() -> new EntidadNoExiste("No se encontro usuario con id: " + id));
        return contratacionRepository.countByOfertanteAndEstado(ofertante, Contratacion.EstadoContratacion.TERMINADA);
    }

    public List<ContratacionTerminadasDTO> obtenerContratacionesTerminadasPorDemandante(String emailDemandante) throws EntidadNoExiste {
        UsuarioDemandante demandante = usuarioDemandanteRepository.findOneByEmail(emailDemandante).orElseThrow(() -> new EntidadNoExiste("No se encontro usuario con email: " + emailDemandante));
        List<Contratacion> contrataciones = contratacionRepository.findAllByDemandanteAndEstado(demandante, Contratacion.EstadoContratacion.TERMINADA).orElseThrow(() -> new EntidadNoExiste("No se encontraron contratacion terminadas para el usuario: " + emailDemandante));
        return contrataciones.stream().
                map(ContratacionMapper::toDto).
                collect(Collectors.toList());
    }

    public List<ContratacionResumenDTO> obtenerContratacionesAceptadasPorOfertante(String emailOfertante) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(emailOfertante).orElseThrow(() -> new EntidadNoExiste("No se encontro usuario con email: " + emailOfertante));
        List<Contratacion> contrataciones = contratacionRepository.findAllByOfertanteAndEstado(ofertante, Contratacion.EstadoContratacion.ACEPTADA).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones aceptadas para el usuario: " + emailOfertante));
        return contrataciones.stream().
                map(ContratacionMapper::toResumenDto).
                collect(Collectors.toList());
    }
}

