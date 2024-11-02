package com.serviciosya.serviciosya_backend.business.controllers;

import com.serviciosya.serviciosya_backend.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class FotoPerfilController {
    @Autowired

    private S3Service s3Service;


    @PostMapping("/{ofertanteId}/foto")
    public ResponseEntity<String> uploadFotoPerfil(
            @PathVariable Long ofertanteId,
            @RequestParam("file") MultipartFile file) {
        String fileName = "ofertante_" + ofertanteId + "_foto.jpg";
        try (InputStream inputStream = file.getInputStream()) {
            String result = s3Service.uploadFile(fileName, inputStream);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al leer el archivo: " + e.getMessage());
        }
    }
}
