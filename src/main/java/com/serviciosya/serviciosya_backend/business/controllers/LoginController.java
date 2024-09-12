package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Administrador;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.UsuarioMgr;
import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class  LoginController {
    @Autowired
    private UsuarioMgr usuarioMgr;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Map<String, String> loginData){
        try {
            String email = loginData.get("usuario");
            String contrasena = loginData.get("contraseña");

            Usuario usuario = usuarioMgr.validarLogin(email, contrasena);

            // cargar  detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());

            // generar token
            String jwtToken = jwtUtils.generateToken(userDetails);

            // Preparar la response con el token y el tipo de usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);

            String tipo = usuarioRepository.findTipoById(usuario.getId());

            if (tipo.equals("ADMINISTRADOR")) {
                response.put("tipo", "administrador");
            } else if (tipo.equals("DEMANDANTE")) {
                response.put("tipo", "demandante");
            } else if (tipo.equals("OFERTANTE")) {
                response.put("tipo", "ofertante");
            } else {
                return ResponseEntity.status(403).body("No se pudo identificar el tipo de usuario");
            }

            response.put("user", buildUserResponse(usuario));

            return ResponseEntity.ok(response);
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(403).body("Usuario o contraseña incorrectos");
        } catch (InvalidInformation e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to build user data for the response
    private Map<String, Object> buildUserResponse(Usuario usuario) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("email", usuario.getEmail());
        userData.put("nombre", usuario.getNombre());
        userData.put("apellido", usuario.getApellido());
        userData.put("telefono", usuario.getTelefono());
        userData.put("direccion", usuario.getDireccion());
        userData.put("genero", usuario.getGenero());
        userData.put("fechaNacimiento", usuario.getFechaNacimiento());
        userData.put("cedula", usuario.getCedula());


        // Add any other fields you want to include
        return userData;
    }



}
