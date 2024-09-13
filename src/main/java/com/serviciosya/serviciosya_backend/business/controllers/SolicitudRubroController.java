package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.SolicitudRubro;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.managers.SolicitudRubroMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/solicitud-rubro")
@CrossOrigin(origins = "http://localhost:3000")
public class SolicitudRubroController {

    @Autowired
    private SolicitudRubroMgr solicitudRubroMgr;

    @PostMapping("/crear")
    public ResponseEntity<?> crearSolicitudRubro(@RequestBody Map<String, Object> payload) {
        try {
            // Desglosar el payload para obtener los IDs
            Long cedulaOfertante = Long.parseLong(payload.get("cedulaOfertante").toString());
            String nombreRubro = payload.get("nombreRubro").toString();

            // Llamar al manager para crear la solicitud
            solicitudRubroMgr.crearSolicitudRubro(cedulaOfertante, nombreRubro);


            // Retornar la respuesta
            return ResponseEntity.ok("Solicitud de rubro creada correctamente");
        } catch (EntidadNoExiste | InvalidInformation e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al crear la solicitud de rubro");
        }
    }
}
