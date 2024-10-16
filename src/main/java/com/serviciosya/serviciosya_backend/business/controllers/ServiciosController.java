package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
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
    @Autowired
    private com.serviciosya.serviciosya_backend.business.utils.JwtService JwtService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/mis-servicios/")
    public ResponseEntity<List<Servicio>> obtenerServiciosDelUsuario(@RequestHeader("Authorization") String token) {
        try {

            String jwtToken = token.substring(7);
            String email = JwtService.getUsernameFromToken(jwtToken);
            UsuarioOfertante usuarioOfertante = (UsuarioOfertante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            List<Servicio> servicios = servicioMgr.obtenerServiciosDeUnUsuario(usuarioOfertante.getId());

            return new ResponseEntity<>(servicios, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
