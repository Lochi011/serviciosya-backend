package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    @Autowired
    private ServicioMgr servicioMgr;

    @Autowired
    private UsuarioMgr usuarioMgr;

    @GetMapping("/servicios-por-rubro/{nombreRubro}")
    public ResponseEntity<?> obtenerServiciosDtoPorRubro(@PathVariable String nombreRubro) {
        try {
            List<ServicioDto> serviciosDto = servicioMgr.obtenerServiciosDtoPorRubro(nombreRubro);
            return new ResponseEntity<>(serviciosDto, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ver-perfil/{ofertante-id}")
    public ResponseEntity<?> verPerfilOfertante(@PathVariable Long ofertanteId) {
        try {
            Map<String, Object> perfil = usuarioMgr.obtenerPerfilOfertante(ofertanteId);
            return new ResponseEntity<>(perfil, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
