package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.dto.ContratacionDTO;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;
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
    private JwtUtils jwtUtils;

    @GetMapping("/contrataciones")
    public ResponseEntity<?> getContratacionesByUserId(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7); // Remueve el prefijo "Bearer "
            String email = jwtUtils.extractUsername(jwtToken);

            UsuarioDemandante usuarioDemandante = (UsuarioDemandante) usuarioRepository.findOneByEmail(email)
                    .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado"));

            List<ContratacionDTO> contratacionDTOs = buildContratacionesDTO(usuarioDemandante.getContrataciones());

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
                    servicio.getNombre(),
                    servicio.getPrecio(),
                    servicio.getUsuarioOfertante().getNombre(),
                    servicio.getUsuarioOfertante().getApellido()
            );
            contratacionDTOs.add(dto);
        }

        return contratacionDTOs;
    }
}
