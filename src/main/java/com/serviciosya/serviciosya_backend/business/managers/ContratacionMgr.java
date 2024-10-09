package com.serviciosya.serviciosya_backend.business.managers;


import com.serviciosya.serviciosya_backend.business.entities.*;
import com.serviciosya.serviciosya_backend.business.entities.dto.ContratacionResumenDTO;
import com.serviciosya.serviciosya_backend.business.entities.mapper.ContratacionMapper;
import com.serviciosya.serviciosya_backend.business.entities.mapper.NotificacionMapper;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.serviciosya.serviciosya_backend.business.entities.Contratacion.EstadoContratacion.PENDIENTE;

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

    public void crearContratacion(Long idDemandante, Long idOfertante, LocalDate fechaServicio, String direccion, String apartamento, String hora, String comentario, Long idServicio) throws EntidadNoExiste, InvalidInformation {
        try {
            if (fechaServicio == null || direccion == null || hora == null || idDemandante == null || idDemandante == null || idServicio == null) {
                throw new InvalidInformation("Faltan datos obligatorios para la realizacion de la contratacion.");

            }

        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneById(Long.valueOf(idOfertante)).orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con id: " + idOfertante));
        UsuarioDemandante demandante = usuarioDemandanteRepository.findOneById(Long.valueOf(idDemandante)).orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con id: " + idDemandante));
        Servicio servicio = servicioRepository.findOneById(Long.valueOf(idServicio)).orElseThrow(() -> new EntidadNoExiste("Servicio no enccontrado con id  " + idServicio));

        //verificar que no exista contratacion pedniente
        Optional<Contratacion> contratacionExistente = contratacionRepository.findByDemandanteAndOfertanteAndServicioAndFechaContratacionAndEstado(demandante, ofertante, servicio, fechaServicio, PENDIENTE);
        if (contratacionExistente.isPresent()) {
            if (contratacionExistente.get().getEstado() == PENDIENTE) {
                throw new InvalidInformation("Ya existe una solicitud pendiente para una contratacion ese dia");
            } else if (contratacionExistente.isPresent()) {
                if (contratacionExistente.get().getEstado() == Contratacion.EstadoContratacion.ACEPTADA) {
                    throw new InvalidInformation("Ya existe una contratacion solicitada para ese dia");
                }

            }
        }

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
            System.out.println("Contratacion creada con ID: " + contratacion.getId() + ", ofertante: " + ofertante.getCedula() + ", servicio: " + servicio.getNombre());
        } catch (Exception e) {
            // Manejar errores de persistencia o inesperados
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
        try { // Guardar la notificacion
            notificacionRepository.save(notificacion);
            System.out.println("Notificacion creada con ID: " + notificacion.getId() + ", ofertante: " + ofertante.getCedula() + ", contratacion: " + contratacion.getId());
        } catch (Exception e) {
            // Manejar errores de persistencia o inesperados
            System.err.println("Error al guardar la notificacion: " + e.getMessage());
            throw new RuntimeException("Error interno al crear la notificacion de la contratacion.");
        }


    }

    public List<Contratacion> obtenerContratacionesPorOfertante(String email) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con email: " + email));
        return contratacionRepository.findAllByOfertante(ofertante).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones para el ofertante con email: " + email));
    }



    public void aceptarContratacion(Long idContratacion) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.ACEPTADA);
        contratacionRepository.save(contratacion);
    }

    public void rechazarContratacion(Long idContratacion) throws EntidadNoExiste {
        Contratacion contratacion = contratacionRepository.findById(idContratacion).orElseThrow(() -> new EntidadNoExiste("Contratacion no encontrada con id: " + idContratacion));
        contratacion.setEstado(Contratacion.EstadoContratacion.RECHAZADA);
        contratacionRepository.save(contratacion);
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

    }

    public List<ContratacionResumenDTO> obtenerContratacionesResumenPorOfertante(String email) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Demandante no encontrado con email: " + email));
        List<Contratacion> contrataciones = contratacionRepository.findAllByOfertante(ofertante).orElseThrow(() -> new EntidadNoExiste("No se encontraron contrataciones para el demandante con email: " + email));
        return contrataciones.stream().
                map(ContratacionMapper::toResumenDto).
                collect(Collectors.toList());
    }
}

