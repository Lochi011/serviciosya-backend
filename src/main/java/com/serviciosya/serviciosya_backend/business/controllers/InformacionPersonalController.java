package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.controllers.auth.ChangePasswordRequest;
import com.serviciosya.serviciosya_backend.business.controllers.auth.ResetPasswordRequest;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import com.serviciosya.serviciosya_backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class InformacionPersonalController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Para encriptar contraseñas

    @Autowired
    private EmailService emailService;  // Servicio de email para enviar correos

    // Método para cambiar la contraseña con autenticación
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request) {
        try {
            // Obtener el email del token JWT
            String jwtToken = token.substring(7); // "Bearer " tiene 7 caracteres
            String email = jwtService.getUsernameFromToken(jwtToken);

            // Buscar el usuario por email
            Usuario usuario = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Verificar si la contraseña actual es correcta
            if (!passwordEncoder.matches(request.getOldPassword(), usuario.getContrasena())) {
                return ResponseEntity.status(403).body("La contraseña actual es incorrecta");
            }

            // Actualizar la contraseña
            usuario.setContrasena(passwordEncoder.encode(request.getNewPassword()));
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Contraseña cambiada exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al cambiar la contraseña: " + e.getMessage());
        }
    }

    // Método para enviar correo de recuperación de contraseña
    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<?> recuperarContrasena(@RequestParam("email") String email) {
        try {
            // Verificamos si el correo existe
            Usuario usuario = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Generamos el token de recuperación
            String token = jwtService.generatePasswordResetToken(email);
            System.out.println("Generado token de recuperación: " + token);

            // Enviamos el correo con el token
            emailService.enviarCorreoRecuperacion(email, token);
            System.out.println("Correo de recuperación enviado a: " + email);

            return ResponseEntity.ok("Correo de recuperación enviado");
        } catch (EntidadNoExiste e) {
            System.err.println("Email no encontrado: " + email);
            return ResponseEntity.status(404).body("Email no encontrado");
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al enviar el correo de recuperación");
        }
    }

    // Método para restablecer la contraseña sin autenticación (olvido de contraseña)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        try {
            // Extraer el email del token JWT
            String email = jwtService.getUsernameFromToken(request.getToken());

            // Buscar al usuario por email
            Usuario usuario = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Actualizar la contraseña con la nueva
            usuario.setContrasena(passwordEncoder.encode(request.getNewPassword()));
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Contraseña restablecida exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al restablecer la contraseña: " + e.getMessage());
        }
    }

    @GetMapping("/personal-info")
    public ResponseEntity<?> getPersonalInfo(@RequestHeader("Authorization") String token) {
        try {
            // Extraer el token JWT del encabezado
            String jwtToken = token.substring(7); // "Bearer " tiene 7 caracteres
            String email = jwtService.getUsernameFromToken(jwtToken);

            // Buscar el usuario en la base de datos
            Usuario usuario = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Construir la respuesta con los datos del usuario
            Map<String, Object> response = buildUserResponse(usuario);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al obtener la información personal: " + e.getMessage());
        }
    }

    // PUT para actualizar la información personal
    @PutMapping("/personal-info")
    public ResponseEntity<?> updatePersonalInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody Usuario updatedInfo) {
        try {
            String jwtToken = token.substring(7);
            String email = jwtService.getUsernameFromToken(jwtToken);

            // Buscar al usuario por email
            Usuario usuario = usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Actualizar los campos con la nueva información
            usuario.setNombre(updatedInfo.getNombre());
            usuario.setApellido(updatedInfo.getApellido());
            usuario.setEmail(updatedInfo.getEmail());
            usuario.setTelefono(updatedInfo.getTelefono());
            usuario.setDireccion(updatedInfo.getDireccion());

            // Guardar los cambios en la base de datos
            usuarioRepository.save(usuario);

            // Responder con la información actualizada
            Map<String, Object> response = buildUserResponse(usuario);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al actualizar la información personal: " + e.getMessage());
        }
    }

    private Map<String, Object> buildUserResponse(Usuario usuario) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("email", usuario.getEmail());
        userData.put("nombre", usuario.getNombre());
        userData.put("apellido", usuario.getApellido());
        userData.put("telefono", usuario.getTelefono());
        userData.put("genero", usuario.getGenero());
        userData.put("fechaNacimiento", usuario.getFechaNacimiento());
        userData.put("cedula", usuario.getCedula());
        userData.put("direccion", usuario.getDireccion());

        return userData;
    }
}



