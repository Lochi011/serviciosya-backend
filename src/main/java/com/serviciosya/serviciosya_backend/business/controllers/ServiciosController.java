package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
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
@RequestMapping("/api/verservicios")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiciosController {

    private static final Logger logger = LoggerFactory.getLogger(ServiciosController.class);
    @Autowired
    private ServicioMgr servicioMgr;

    @GetMapping("/mis-servicios")
    public ResponseEntity<List<Servicio>> obtenerServiciosDelUsuario(@PathVariable Long usuarioId){
        try{
            List<Servicio> servicios = servicioMgr.obtenerServiciosDeUnUsuario(usuarioId);
            System.out.println("Servicios: " + servicios);

            return new ResponseEntity<>(servicios, HttpStatus.OK);

        } catch (EntidadNoExiste e) {
            throw new RuntimeException(e);
        }
    }


}
