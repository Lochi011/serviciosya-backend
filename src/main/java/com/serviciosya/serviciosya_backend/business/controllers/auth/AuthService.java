package com.serviciosya.serviciosya_backend.business.controllers.auth;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.utils.JwtService;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findOneByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (usuario.getContrasena().equals(request.getContraseña()))
            usuario.setContrasena(passwordEncoder.encode(request.getContraseña()));
            usuarioRepository.save(usuario);

        if (request.getContraseña() == null || request.getContraseña().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getContraseña()));
        UserDetails user = usuarioRepository.findOneByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }
    public AuthResponse register(RegisterRequest request) {
        if (request.getContrasena() == null || request.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        Usuario usuario = Usuario.builder()
                .cedula(request.getCedula())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .direccion(request.getDireccion())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .genero(request.getGenero())
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaCreacion(new Date())
                .role(Usuario.Role.DEMANDANTE)
                .build();
        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
}
