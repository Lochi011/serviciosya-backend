package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Administrador;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    @Autowired
    private UsuarioMgr usuarioMgr;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Map<String, String> loginData){
        try {
            String email = loginData.get("usuario");
            String contrasena = loginData.get("contraseña");

            Usuario usuario = usuarioMgr.validarLogin(email, contrasena);
            if (usuario instanceof Administrador) {
                return ResponseEntity.ok("administrador");
            } else if (usuario instanceof UsuarioOfertante) {
                return ResponseEntity.ok("ofertante");
            } else if (usuario instanceof UsuarioDemandante) {
                return ResponseEntity.ok("demandante");
            } else {
                return ResponseEntity.status(403).body("Usuario o contraseña incorrectos");
            }
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(403).body("Usuario o contraseña incorrectos");
        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }
    }
}
