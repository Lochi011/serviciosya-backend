package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitud-rubro")
@CrossOrigin(origins = "http://localhost:3000")
public class SolicitudRubroController {

    // Declarar el logger
    private static final Logger logger = LoggerFactory.getLogger(SolicitudRubroController.class);


    @Autowired
    private SolicitudRubroMgr solicitudRubroMgr;

    @PostMapping("/crear")
    public ResponseEntity<?> crearSolicitudRubro(@RequestBody Map<String, Object> payload) {
        try {
            // Validar y desglosar el payload
            if (!payload.containsKey("cedulaOfertante") || !payload.containsKey("nombreRubro")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incompletos para crear la solicitud.");
            }

            Long cedulaOfertante = Long.parseLong(payload.get("cedulaOfertante").toString().trim());
            String nombreRubro = payload.get("nombreRubro").toString();

            // Log para verificación de los valores antes de crear la solicitud
            logger.info("Intentando crear solicitud para cédula: {}, rubro: {}", cedulaOfertante, nombreRubro);

            // Llamar al manager para crear la solicitud
            solicitudRubroMgr.crearSolicitudRubro(cedulaOfertante, nombreRubro);

            // Log para seguimiento
            logger.info("Solicitud de rubro creada para ofertante con cédula: {}", cedulaOfertante);

            // Retornar la respuesta
            return ResponseEntity.ok("Solicitud de rubro creada correctamente");
        } catch (EntidadNoExiste | InvalidInformation e) {
            // Log del error con mensaje específico
            logger.error("Error al crear solicitud de rubro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NumberFormatException e) {
            // Manejo específico si hay un error al convertir los datos
            logger.error("Error de formato en los datos recibidos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada: " + e.getMessage());
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error interno al crear la solicitud de rubro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al crear la solicitud de rubro");
        }
    }


    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudRubro>> obtenerSolicitudesPendientes() {
        try {
            // Llamamos a la función en el manager para obtener las solicitudes pendientes
            List<SolicitudRubro> solicitudesPendientes = solicitudRubroMgr.obtenerSolicitudesPendientes();
            return new ResponseEntity<>(solicitudesPendientes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{solicitudId}/rechazar")
    public ResponseEntity<String> rechazarSolicitud(@PathVariable Long solicitudId) {
        try {
            // Llamamos al manager para rechazar la solicitud
            solicitudRubroMgr.rechazarSolicitud(solicitudId);
            //posible agregra comentario de porque fue rechazado, ver mas adelante

            // Mensaje de éxito
            return new ResponseEntity<>("Solicitud rechazada con éxito.", HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            // Si no se encuentra la solicitud
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Cualquier otro error inesperado
            return new ResponseEntity<>("Error interno al rechazar la solicitud.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{solicitudId}/aprobar")
    public ResponseEntity<String> aprobarSolicitud(@PathVariable Long solicitudId) {
        try {
            // Llamamos al manager para aprobar la solicitud
            solicitudRubroMgr.aprobarSolicitud(solicitudId);

            // Mensaje de éxito
            return new ResponseEntity<>("Solicitud aprobada con éxito.", HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            // Si no se encuentra la solicitud o el ofertante
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Cualquier otro error inesperado
            return new ResponseEntity<>("Error interno al aprobar la solicitud.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
