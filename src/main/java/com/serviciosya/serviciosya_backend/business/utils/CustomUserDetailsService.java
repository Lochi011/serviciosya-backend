package com.serviciosya.serviciosya_backend.business.utils;


import com.serviciosya.serviciosya_backend.business.entities.Administrador;
import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from the database
        Usuario usuario = usuarioRepository.findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Example: Set roles dynamically based on the user's properties
        List<SimpleGrantedAuthority> authorities = getAuthoritiesForUser(usuario);

        // Return UserDetails object with username, password, and assigned roles
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getContrasena(),
                authorities
        );
    }

    // Custom method to assign roles based on user data
    private List<SimpleGrantedAuthority> getAuthoritiesForUser(Usuario usuario) {
        // Example: Add different roles based on the user type
        if (usuario instanceof Administrador) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (usuario instanceof UsuarioOfertante) {
            return List.of(new SimpleGrantedAuthority("ROLE_OFERTANTE"));
        } else if (usuario instanceof UsuarioDemandante) {
            return List.of(new SimpleGrantedAuthority("ROLE_DEMANDANTE"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Default role
        }
    }
}

