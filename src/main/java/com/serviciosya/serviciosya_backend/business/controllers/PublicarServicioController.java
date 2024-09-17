package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

// Ejemplo de un Controller en Spring Boot
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicarServicioController {

    private static final Logger logger = LoggerFactory.getLogger(PublicarServicioController.class);

    @Autowired
    private ServicioMgr servicioMgr;

    @PostMapping("/publicar")
    public ResponseEntity<?> publicarServicio(@RequestBody Map<String, Object> payload) {
        try {
            // Extraer los parámetros necesarios del payload
            Long cedula = Long.valueOf(payload.get("cedula").toString());
            String tituloServicio = (String) payload.get("tituloServicio");
            int precio = Integer.parseInt(payload.get("precio").toString());
            String horaDesde = (String) payload.get("horaDesde");
            String horaHasta = (String) payload.get("horaHasta");
            List<String> selectedDays = (List<String>) payload.get("selectedDays");
            int duracionServicio = Integer.parseInt(payload.get("duracionServicio").toString());
            List<String> tags = (List<String>) payload.get("tags");
            String descripcion = (String) payload.get("descripcion");
            String nombreRubro = (String) payload.get("nombreRubro"); // Nuevo campo para el nombre del rubro

            // Llamar al manager para publicar el servicio
            servicioMgr.publicarServicio(cedula, tituloServicio, precio, horaDesde, horaHasta, selectedDays,
                    duracionServicio, tags, descripcion, nombreRubro); // Pasamos el nombre del rubro

            return ResponseEntity.ok("Servicio publicado exitosamente en el rubro: " + nombreRubro + ".");
        } catch (EntidadNoExiste | InvalidInformation e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Manejo de errores inesperados
            logger.error("Error inesperado al publicar el servicio", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al publicar el servicio.");
        }
    }


}
