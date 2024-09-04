package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Ejemplo de un Controller en Spring Boot
@RestController
@RequestMapping("/api/servicios")
public class PublicarServicioController {

    @Autowired
    private ServicioMgr servicioMgr;

    @PostMapping("/publicar")
    public ResponseEntity<?> publicarServicio(@RequestBody Map<String, Object> payload) {
        try {
            servicioMgr.publicarServicio(payload);
            return ResponseEntity.ok("Servicio publicado exitosamente.");
        } catch (EntidadNoExiste | InvalidInformation e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
