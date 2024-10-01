package com.serviciosya.serviciosya_backend.business.controllers.auth;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.exceptions.UsuarioYaExiste;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrarseController {

    @Autowired
    private UsuarioMgr usuarioMgr;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> userData) {
        try {
            // Extraer datos
            Long cedula = Long.parseLong(userData.get("identity"));
            String nombre = userData.get("firstName");
            String apellido = userData.get("lastName");
            String direccion = userData.get("address");
            String email = userData.get("email");
            String telefono = userData.get("phone");
            String contrasena = userData.get("password");
            String genero = userData.get("gender");
            Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(userData.get("birthdate"));
            String tipo = userData.get("accountType");

            System.out.println("ENTRE CONTROLLER " + nombre + " " + cedula + " " + contrasena + " " + email + " " + tipo);

            // Registrar usuario usando UsuarioMgr
            Usuario nuevoUsuario = usuarioMgr.registrarUsuario(
                    cedula, nombre, apellido, direccion, email, telefono, contrasena, genero, fechaNacimiento, tipo
            );

            return ResponseEntity.ok("Usuario registrado exitosamente");

        } catch (UsuarioYaExiste e) {
            System.out.println("YA existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese correo o cédula");
        } catch (InvalidInformation e) {
            System.out.println("Informacion Invalida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Información inválida");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}
