package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.NotificacionDemandante;
import com.serviciosya.serviciosya_backend.business.entities.Rubro;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;
import com.serviciosya.serviciosya_backend.business.entities.mapper.ServicioMapper;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.persistance.RubroRepository;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

public class ServicioMgr {
    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private ServicioRepository servicioRepository;


    private static final Logger logger = LoggerFactory.getLogger(ServicioMgr.class);

    public void publicarServicio(Long cedula, String tituloServicio, int precio, String horaDesde, String horaHasta,
                                 List<String> selectedDays, int duracionServicio, List<String> tags,
                                 String descripcion, String nombreRubro) throws InvalidInformation, EntidadNoExiste {

        // Registro del inicio de la operación
        logger.info("Iniciando la publicación del servicio para el ofertante con cédula: {}", cedula);

        try {
            // Validar campos obligatorios antes de procesar
            if (cedula == null || tituloServicio == null || precio <= 0 || horaDesde == null || horaHasta == null ||
                    selectedDays == null || selectedDays.isEmpty() || duracionServicio < -1 || descripcion == null ||
                    nombreRubro == null || nombreRubro.trim().isEmpty()) {
                logger.error("Faltan datos obligatorios para la publicación del servicio.");
                throw new InvalidInformation("Faltan datos obligatorios para la publicación del servicio.");
            }
            System.out.println(cedula);

            // Buscar el ofertante por su cédula
            UsuarioOfertante ofertante = usuarioOfertanteRepository.findByCedulaWithRubros(cedula)
                    .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con la cédula: " + cedula));

            // Registro de información del ofertante encontrado
            logger.info("Ofertante encontrado: {} {}", ofertante.getNombre(), ofertante.getApellido());

            // Buscar el rubro por su nombre (trim para evitar problemas de espacios)
            Rubro rubro = rubroRepository.findOneByNombre(nombreRubro.trim())
                    .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado con el nombre: " + nombreRubro));

            // Verificar si el ofertante está autorizado para publicar en el rubro
            boolean autorizado = ofertante.getRubros() != null &&
                    ofertante.getRubros().stream().anyMatch(r -> r.getId().equals(rubro.getId()));

            // Lanzar error si el ofertante no está autorizado
            if (!autorizado) {
                logger.error("El ofertante no está autorizado para publicar en el rubro: {}", rubro.getNombre());
                throw new InvalidInformation("NOT_AUTHORIZED_FOR_CATEGORY", "No autorizado para publicar en este rubro.");
            }

            boolean servicioExistente = servicioRepository.existsByNombreAndDescripcionAndPrecioAndHoraDesdeAndHoraHastaAndUsuarioOfertanteAndRubro(
                    tituloServicio, descripcion, precio, horaDesde, horaHasta, ofertante, rubro);

            if (servicioExistente) {
                logger.error("Ya existe un servicio idéntico para este ofertante.");
                throw new InvalidInformation("DUPLICATE_SERVICE", "Ya existe un servicio igual publicado.");
            }

            // Construir el objeto Servicio
            Servicio servicio = Servicio.builder()
                    .nombre(tituloServicio)
                    .descripcion(descripcion)
                    .precio(precio)
                    .horaDesde(horaDesde)  // Asigna la hora de inicio
                    .horaHasta(horaHasta)  // Asigna la hora de fin
                    .diasSeleccionados(selectedDays)  // Asigna los días seleccionados
                    .duracionServicio(duracionServicio)  // Asigna la duración del servicio
                    .etiquetas(tags)
                    .usuarioOfertante(ofertante)
                    .rubro(rubro)
                    .build();

            // Guardar el servicio en el repositorio
            servicioRepository.save(servicio);

            // Registro de la publicación exitosa del servicio
            logger.info("Servicio publicado exitosamente con ID: {} para el ofertante: {} en el rubro: {}",
                    servicio.getId(), ofertante.getCedula(), rubro.getNombre());

        } catch (EntidadNoExiste e) {
            // Log del error si el ofertante o el rubro no existen
            logger.error("Error al publicar el servicio: {}", e.getMessage());
            throw e;
        } catch (InvalidInformation e) {
            // Log del error si la información proporcionada es inválida
            logger.error("Información inválida al publicar el servicio: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log del error inesperado y lanzamiento de excepción genérica
            logger.error("Error inesperado al publicar el servicio: {}", e.getMessage(), e);
            throw new RuntimeException("Error interno al publicar el servicio.", e);
        }
    }

    public List<Servicio> obtenerServiciosPorRubro (String nombreRubro) throws EntidadNoExiste {

        Rubro rubro = rubroRepository.findOneByNombre(nombreRubro)
                .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado con el nombre: " + nombreRubro));

        return servicioRepository.findAllByRubro(rubro)
                .orElseThrow(() -> new EntidadNoExiste("NO_SERVICES","No se encontraron servicios para el rubro: " + nombreRubro));
    }

    public List<ServicioDto> obtenerServiciosDtoPorRubro(String nombreRubro) throws EntidadNoExiste {
        Rubro rubro = rubroRepository.findOneByNombre(nombreRubro)
                .orElseThrow(() -> new EntidadNoExiste("Rubro no encontrado con el nombre: " + nombreRubro));

        List<Servicio> servicios = servicioRepository.findAllByRubro(rubro)
                .orElseThrow(() -> new EntidadNoExiste("NO_SERVICES", "No se encontraron servicios para el rubro: " + nombreRubro));

        return servicios.stream()
                .map(ServicioMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ServicioDto> obtenerServiciosDtoPorOfertante(String email) throws EntidadNoExiste {
        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneByEmail(email)
                .orElseThrow(() -> new EntidadNoExiste("Ofertante no encontrado con el email: " + email));

        List<Servicio> servicios = servicioRepository.findAllByUsuarioOfertante(ofertante)
                .orElseThrow(() -> new EntidadNoExiste("NO_SERVICES", "No se encontraron servicios para el ofertante: " + email));

        return servicios.stream()
                .map(ServicioMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ServicioDto> obtenerServiciosDeUnUsuario (Long id) throws EntidadNoExiste {

        UsuarioOfertante ofertante = usuarioOfertanteRepository.findOneById(id).orElseThrow(() -> new EntidadNoExiste("No se encontro usuario con id: " + id)) ;
        Optional<List<Servicio>> servicios = servicioRepository.findAllByUsuarioOfertante(ofertante);
        List<ServicioDto> serviciosDto = servicios.get().stream().map(ServicioMapper::toDto).collect(Collectors.toList());

        return serviciosDto;
    }

    public  Servicio modificarServicio(Long id,String nombre, String descripcion, int precio, String horaDesde, String horaHasta, int duracion, List<String> etiquetas, List<String> diasSeleccionados) throws EntidadNoExiste {
        Servicio servicio = servicioRepository.findOneById(id).orElseThrow(() -> new EntidadNoExiste("No se encontro servicio con id: " + id));
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setPrecio(precio);
        servicio.setHoraDesde(horaDesde);
        servicio.setHoraHasta(horaHasta);
        servicio.setDuracionServicio(duracion);
        servicio.setEtiquetas(etiquetas);
        servicio.setDiasSeleccionados(diasSeleccionados);
        servicioRepository.save(servicio);


        return servicio;

    }
    public Servicio eliminarServicio(Long id ) throws EntidadNoExiste{
        Servicio servicio = servicioRepository.findOneById(id).orElseThrow(() -> new EntidadNoExiste("No se encontro servicio con id:"  + id));
        servicioRepository.delete(servicio);

        return servicio;
    }






}



