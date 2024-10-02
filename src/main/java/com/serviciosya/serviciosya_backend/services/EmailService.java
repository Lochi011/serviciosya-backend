package com.serviciosya.serviciosya_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoRecuperacion(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de contraseña");
        message.setText("Haz clic en el siguiente enlace para recuperar tu contraseña: " +
                "http://localhost:3000/reset-password?token=" + token);

        mailSender.send(message);
        System.out.println("Correo de recuperación enviado a: " + email);
    }
}

