package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import com.serviciosya.serviciosya_backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;

@RestController
@RequestMapping("/api/recuperar-contrasena")
public class RecuperarContrasenaController {

    @Autowired
    private UsuarioMgr usuarioMgr; // Inyectamos el manager de usuario
    @Autowired
    private JwtUtils jwtUtils;  // Inyectamos JwtUtils para usar el método de generación de token
    @Autowired
    private EmailService emailService; // Inyectamos el servicio de correo


    @PostMapping
    public ResponseEntity<String> recuperarContrasena(@RequestParam("email") String email) {
        try {
            // Verificamos si el correo existe usando el método del UsuarioMgr
            Usuario usuario = usuarioMgr.obtenerUnoPorCorreo(email);

            // Generamos el token de recuperación
            String token = jwtUtils.generatePasswordResetToken(email);

            // Lógica para enviar el correo con el token (lo haremos en los próximos pasos)
            emailService.enviarCorreoRecuperacion(email, token);

            return ResponseEntity.ok("Correo de recuperación enviado");
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(404).body("Email no encontrado");
        }
    }
}
