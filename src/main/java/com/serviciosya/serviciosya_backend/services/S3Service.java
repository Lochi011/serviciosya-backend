package com.serviciosya.serviciosya_backend.services;

import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.persistance.UsuarioOfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;


@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    private UsuarioOfertanteRepository usuarioOfertanteRepository;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String keyName, InputStream fileInputStream, Long ofertanteId) {
        try {
            UsuarioOfertante usuarioOfertante = usuarioOfertanteRepository.findById(ofertanteId).orElseThrow(() -> new RuntimeException("No se encontró el ofertante con el id: " + ofertanteId));

            if (usuarioOfertante.getFotoPerfil() != null) {
                deleteFile(usuarioOfertante.getFotoPerfil());
            }

            usuarioOfertante.setFotoPerfil(keyName);


            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, fileInputStream.available()));

            // Guardar el nombre del archivo (key) en la BD
            usuarioOfertante.setFotoPerfil(keyName);
            usuarioOfertanteRepository.save(usuarioOfertante);

            return "Archivo subido con éxito a S3 con el nombre de archivo: " + keyName;
        } catch (S3Exception e) {
            throw new RuntimeException("Error al subir archivo a S3: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void deleteFile(String keyName) {
        try {
            s3Client.deleteObject(b -> b.bucket(bucketName).key(keyName));
        } catch (S3Exception e) {
            throw new RuntimeException("Error al eliminar archivo de S3: " + e.getMessage());
        }
    }
}
