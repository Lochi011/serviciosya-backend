package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.dto.ContratacionDTO;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.ContratacionRepository;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class HistorialController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContratacionRepository contratacionRepository;

    @Autowired
    private JwtService JwtService;

    @GetMapping("/contrataciones")
    public ResponseEntity<?> getContratacionesByUserId(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("Token recibido: " + token);

            String jwtToken = token.substring(7); // Remueve el prefijo "Bearer "
            String email = JwtService.getUsernameFromToken(jwtToken);
            System.out.println("\n" +  email + "\n");

            UsuarioDemandante usuarioDemandante = (UsuarioDemandante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            List<Contratacion> contrataciones = contratacionRepository.findByDemandante(usuarioDemandante);
            List<ContratacionDTO> contratacionDTOs = buildContratacionesDTO(contrataciones);

            return ResponseEntity.ok(contratacionDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al obtener las contrataciones: " + e.getMessage());
        }
    }

    // Método para construir los DTOs desde las entidades Contratacion
    private List<ContratacionDTO> buildContratacionesDTO(List<Contratacion> contrataciones) {
        List<ContratacionDTO> contratacionDTOs = new ArrayList<>();

        for (Contratacion contratacion : contrataciones) {
            Servicio servicio = contratacion.getServicio();

            ContratacionDTO dto = new ContratacionDTO(
                    contratacion.getId(),
                    servicio.getNombre(),
                    servicio.getPrecio(),
                    servicio.getUsuarioOfertante().getNombre(),
                    servicio.getUsuarioOfertante().getApellido(),
                    contratacion.getFechaContratacion(),
                    contratacion.getHora(),
                    contratacion.getEstado().toString()

            );
            contratacionDTOs.add(dto);
        }

        return contratacionDTOs;
    }

    @PutMapping("/contrataciones/{id}/calificar")
    public ResponseEntity<?> calificarServicio(@PathVariable Long id, @RequestParam Float puntuacion, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String email = JwtService.getUsernameFromToken(jwtToken);
            UsuarioDemandante usuarioDemandante = (UsuarioDemandante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            Contratacion contratacion = contratacionRepository.findById(id)
                    .orElseThrow(() -> new EntidadNoExiste("Contratación no encontrada"));

            // Verificar si el demandante de la contratación es el mismo que está autenticado
            if (!contratacion.getDemandante().equals(usuarioDemandante)) {
                return ResponseEntity.status(403).body("No tienes permiso para calificar este servicio.");
            }

            // Actualizar la puntuación de la contratación
            contratacion.setPuntuacion(puntuacion);
            contratacionRepository.save(contratacion);

            return ResponseEntity.ok("Puntuación actualizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error al calificar el servicio: " + e.getMessage());
        }
    }



}
