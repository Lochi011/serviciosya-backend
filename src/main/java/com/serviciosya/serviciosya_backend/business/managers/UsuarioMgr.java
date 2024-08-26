package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.exceptions.UsuarioYaExiste;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMgr {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void agregarUsuario (Usuario usuario) throws InvalidInformation, UsuarioYaExiste{
    if (usuario.getNombre() == null || "".equals(usuario.getNombre())
            || usuario.getApellido() == null || "".equals(usuario.getApellido())
            || usuario.getEmail() == null || "".equals(usuario.getEmail())
            //Validar longitud y formato
            || usuario.getContrasena() == null || "".equals(usuario.getContrasena())
            ) {
        throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
    }

    // Verifico si el usuario ya existe
        if (usuarioRepository.findOneByCedula(usuario.getCedula()) != null) {
        throw new UsuarioYaExiste("Ya existe usuario con esa cedula");
    }
        if (usuarioRepository.findOneByEmail(usuario.getEmail()) != null) {
        throw new UsuarioYaExiste("Ya existe usuario con ese correo");
    }

        usuarioRepository.save(usuario);

}
}
