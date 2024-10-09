package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.business.entities.dto.ServicioDto;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.managers.ServicioMgr;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/mis-servicios")
@CrossOrigin(origins = "http://localhost:3000")
public class MisServiciosController {

    @Autowired
    private ServicioMgr servicioMgr;

    @Autowired
    private JwtService jwtService;


    @GetMapping("/listar-todos")
    public ResponseEntity <?> listarServiciosOfertante(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);
        try {
            List<ServicioDto> servicios = servicioMgr.obtenerServiciosDtoPorOfertante(email);
            return new ResponseEntity < > (servicios, HttpStatus.OK);
        } catch (EntidadNoExiste e) {
            return new ResponseEntity < > (e.getCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

     
}
