package com.serviciosya.serviciosya_backend.business.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/mis-servicios")
@CrossOrigin(origins = "http://localhost:3000")
public class MisServiciosController {

    @GetMapping("/listar-todos")
    public ResponseEntity <?> listarServiciosOfertante(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtService.getUsernameFromToken(jwtToken);

    }
}
