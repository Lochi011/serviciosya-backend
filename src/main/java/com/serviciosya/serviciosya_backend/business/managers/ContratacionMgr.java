package com.serviciosya.serviciosya_backend.business.managers;


import com.serviciosya.serviciosya_backend.business.entities.*;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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
        Optional<Contratacion> contratacionExistente = contratacionRepository.findByDemandanteAndOfertanteAndServicioAndFechaAndEstado(demandante, ofertante, servicio, fechaServicio, PENDIENTE);
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
                    .fecha(fechaServicio)
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




    }

}

