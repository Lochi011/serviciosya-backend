package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ContratacionMgr;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contrataciones")
@CrossOrigin(origins = "http://localhost:3000")
@Validated // Añadir para habilitar la validación
public class ContratacionController {
    private static final Logger logger = LoggerFactory.getLogger(ContratacionController.class);

    @Autowired
    private ContratacionMgr contratacionMgr;

    @PostMapping("/crear")
    public ResponseEntity<?> contratarServicio(@RequestBody Map<String, Object> payload) {
        try {
            // Extraer los parámetros necesarios del payload
            Integer usuarioDemandanteId = (Integer) payload.get("usuarioDemandanteId");
            Long idDemandante = usuarioDemandanteId.longValue();
            Integer usuarioOfertanteIdId = (Integer) payload.get("ofertanteId");
            Long idOfertante = usuarioOfertanteIdId.longValue();;
            LocalDate fechaServicio = LocalDate.parse((String) payload.get("dia"));
            String direccion = (String) payload.get("direccion");
            String apartamento = (String) payload.get("apto");
            String hora = (String) payload.get("hora");
            String comentario = (String) payload.get("comentario");
            Integer servicioIdInt = (Integer) payload.get("idServicio");
            Long idServicio = Long.valueOf(servicioIdInt);


            // Llamar al manager para contratar el servicio
            contratacionMgr.crearContratacion(idDemandante, idOfertante, fechaServicio, direccion,apartamento, hora, comentario, idServicio);

            return ResponseEntity.ok("Contratación realizada exitosamente.");
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidInformation e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("errorCode", e.getCode(), "message", e.getMessage()));
        } catch (Exception e) {
            // Manejo de errores inesperados
            logger.error("Error inesperado al contratar el servicio", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al contratar el servicio. ");
        }
    }



    }
