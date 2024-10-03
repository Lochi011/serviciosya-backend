package com.serviciosya.serviciosya_backend.business.controllers.auth;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
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

        Usuario usuario = usuarioRepository.findOneByEmail(request.getUsuario())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (usuario.getContrasena().equals(request.getContraseña()))
            usuario.setContrasena(passwordEncoder.encode(request.getContraseña()));
            usuarioRepository.save(usuario);

        if (request.getContraseña() == null || request.getContraseña().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsuario(),request.getContraseña()));
        Usuario user = usuarioRepository.findOneByEmail(request.getUsuario()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }
    public AuthResponse register(RegisterRequest request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Usuario usuario;

        switch (request.getAccountType()){

            case "DEMANDANTE":
                usuario = new UsuarioDemandante();
                usuario.setRole(Usuario.Role.OFERTANTE);
                break;

            case "OFERTANTE":
                usuario = new UsuarioOfertante();
                usuario.setRole(Usuario.Role.OFERTANTE);
                break;
            default:
                throw new IllegalArgumentException("Invalid Account Type");
        }


        usuario.setCedula(request.getIdentity());
        usuario.setNombre(request.getFirstName());
        usuario.setApellido(request.getLastName());
        usuario.setDireccion(request.getAddress());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getPhone());
        usuario.setContrasena(passwordEncoder.encode(request.getPassword()));
        usuario.setGenero(request.getGender());
        usuario.setFechaNacimiento(request.getBirthDate());
        usuario.setFechaCreacion(new Date());


        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
}
