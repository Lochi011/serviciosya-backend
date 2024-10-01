package com.serviciosya.serviciosya_backend.business.controllers.auth;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;
    public AuthResponse login(LoginRequest request) {
        return null;

    }
    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .cedula(request.getCedula())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .direccion(request.getDireccion())
                .email(request.getEmail())
                .contrasena(request.getContrasena())
                .genero(request.getGenero())
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaCreacion(new Date())
                .role(Usuario.Role.DEMANDANTE)
                .build();
        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build()
    }
}
