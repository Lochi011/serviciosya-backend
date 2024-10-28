package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.Notificacion;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDTO;
import com.serviciosya.serviciosya_backend.business.entities.dto.NotificacionDemandanteDTO;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.managers.NotificacionMgr;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:3000")

public class NotificacionController {

    @Autowired
    private NotificacionMgr notificacionMgr;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/no-leidas/")
    public ResponseEntity<?> obtenerNotificacionesNoLeidasPorUsuarioOferanteId( @RequestHeader("Authorization") String token ){

        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
        List<NotificacionDTO> notificaciones = notificacionMgr.obtenerNotificacionesDTONoLeidasPorUsuarioOferanteEmail(email);
            return new ResponseEntity<>(notificaciones, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/todas/")
    public ResponseEntity<?> obtenerNotificacionesPorUsuarioOferanteId( @RequestHeader("Authorization") String token ){

        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
        List<NotificacionDTO> notificaciones = notificacionMgr.obtenerNotificacionesDTOPorUsuarioOferanteEmail(email);
            return new ResponseEntity<>(notificaciones, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/marcar-leida/{id}")
    public ResponseEntity<?> marcarNotificacionComoLeida(@PathVariable Long id) {
        try {
            notificacionMgr.marcarNotificacionComoLeida(id);
            return ResponseEntity.ok("Notificación marcada como leída.");
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/numero-no-leidas")
    public ResponseEntity <?> contarNotificacionesNoLeidasPorUsuarioOferanteEmail(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
            Long cantidad = notificacionMgr.contarNotificacionesNoLeidasPorUsuarioOferanteEmail(email);
            return new ResponseEntity < > (cantidad, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity < > (e.getCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/no-leidas-demandante")
    public ResponseEntity<?> obtenerNotificacionesNoLeidasPorUsuarioDemandante( @RequestHeader("Authorization") String token ){

        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
        List<NotificacionDemandanteDTO> notificaciones = notificacionMgr.obtenerNotificacionesDTONoLeidasPorUsuarioDemandanteEmail(email);
            return new ResponseEntity<>(notificaciones, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/marcar-leida-demandante/{id}")
    public ResponseEntity<?> marcarNotificacionComoLeidaDemandante(@PathVariable Long id) {
        try {
            notificacionMgr.marcarNotificacionComoLeidaDemandante(id);
            return ResponseEntity.ok("Notificación marcada como leída.");
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/numero-no-leidas-demandante")
    public ResponseEntity <?> contarNotificacionesNoLeidasPorUsuarioDemandanteEmail(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
            Long cantidad = notificacionMgr.contarNotificacionesNoLeidasPorUsuarioDemandanteEmail(email);
            return new ResponseEntity < > (cantidad, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
