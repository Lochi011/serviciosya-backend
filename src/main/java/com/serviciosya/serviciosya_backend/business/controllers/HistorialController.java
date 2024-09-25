package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import com.serviciosya.serviciosya_backend.persistance.ServicioRepository;
import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class HistorialController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // GET para obtener los servicios contratados por un usuario demandante
    @GetMapping("/contrataciones")
    public ResponseEntity<?> getContratacionesByUserId(@RequestHeader("Authorization") String token) {
        try {
            // Extraer el token JWT del encabezado
            String jwtToken = token.substring(7); // "Bearer " tiene 7 caracteres
            String email = jwtUtils.extractUsername(jwtToken);

            // Buscar el usuario en la base de datos por su email
            UsuarioDemandante usuarioDemandante = (UsuarioDemandante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            // Obtener los servicios contratados por el usuario demandante
            List<Servicio> serviciosContratados = usuarioDemandante.getServicios();

            // Construir la respuesta con detalles de los servicios contratados
            Map<String, Object> response = buildUserWithServiciosResponse(serviciosContratados);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al obtener las contrataciones: " + e.getMessage());
        }
    }

    // Construir respuesta con los servicios contratados, incluyendo detalles adicionales
    private Map<String, Object> buildUserWithServiciosResponse(List<Servicio> serviciosContratados) throws EntidadNoExiste {
        Map<String, Object> contratacionesData = new HashMap<>();
        for (Servicio servicio : serviciosContratados) {

            // Obtener el proveedor (usuario que ofrece el servicio)
            Usuario proveedor = usuarioRepository.findById(servicio.getId())
                    .orElseThrow(() -> new EntidadNoExiste("Proveedor no encontrado"));

            // Crear la estructura de respuesta
            Map<String, Object> servicioData = new HashMap<>();
            servicioData.put("id", servicio.getId());
            servicioData.put("nombreServicio", servicio.getNombre());
            servicioData.put("descripcion", servicio.getDescripcion());
            servicioData.put("precio", servicio.getPrecio());  // Precio del servicio

            // Información del proveedor
            servicioData.put("nombreProveedor", proveedor.getNombre());
            servicioData.put("apellidoProveedor", proveedor.getApellido());

            contratacionesData.put(servicio.getId().toString(), servicioData);
        }
        return contratacionesData;
    }
}
