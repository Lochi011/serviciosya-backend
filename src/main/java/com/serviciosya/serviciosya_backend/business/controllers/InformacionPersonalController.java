package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private JwtUtils jwtUtils;

    @GetMapping("/personal-info")
    public ResponseEntity<?> getPersonalInfo(@RequestHeader("Authorization") String token) {
        try {
            // Extraer el token JWT del encabezado
            String jwtToken = token.substring(7); // "Bearer " tiene 7 caracteres
            String email = jwtUtils.extractUsername(jwtToken);

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
            String email = jwtUtils.extractUsername(jwtToken);

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



