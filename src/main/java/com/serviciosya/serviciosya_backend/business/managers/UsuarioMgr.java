package com.serviciosya.serviciosya_backend.business.managers;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import com.serviciosya.serviciosya_backend.business.exceptions.EntidadNoExiste;
import com.serviciosya.serviciosya_backend.business.exceptions.InvalidInformation;
import com.serviciosya.serviciosya_backend.business.exceptions.UsuarioYaExiste;
import com.serviciosya.serviciosya_backend.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsuarioMgr {
    @Autowired
    private UsuarioRepository usuarioRepository;




    public void agregarUsuario(Usuario usuario) throws InvalidInformation, UsuarioYaExiste {

        // Verifico si el usuario ya existe
        if (usuarioRepository.findOneByCedula(usuario.getCedula()) != null) {
            throw new UsuarioYaExiste("Ya existe usuario con esa cedula");
        }
        if (usuarioRepository.findOneByEmail(usuario.getEmail()) != null) {
            throw new UsuarioYaExiste("Ya existe usuario con ese correo");
        }

        usuarioRepository.save(usuario);

    }
    public boolean validarEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }


    public boolean validarDatosLogin(String email, String contrasena) {
        if (email == null || contrasena == null || email.equals("") || contrasena.equals("")) {
            return false;
        }
        if (!validarEmail(email)) {
            return false;
        }
        return true;

    }



    public Usuario validarLogin(String email, String contrasena) throws InvalidInformation, EntidadNoExiste {
        if (!validarDatosLogin(email, contrasena)) {
            throw new InvalidInformation("Datos de login incorrectos");
        }
        if (usuarioRepository.findOneByEmail(email) != null) {
            Usuario usuario = usuarioRepository.findOneByEmail(email);
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
            else {
                throw new InvalidInformation("Contraseña incorrecta");
            }
        } else {
            throw new EntidadNoExiste("Usuario no existe");
        }

    }



    public Usuario registrarUsuario (Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, String genero, Date fechaNacimiento, String tipo) throws InvalidInformation, UsuarioYaExiste {

        if (tipo == "DEMANDANTE") {
            Date fechaCreacion = new Date();
            UsuarioDemandante usuario = new UsuarioDemandante(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
            agregarUsuario(usuario);
            return usuario;
        } else if (tipo == "OFERTANTE"){
            Date fechaCreacion = new Date();
            UsuarioOfertante usuario = new UsuarioOfertante(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
            agregarUsuario(usuario);
            return usuario;
        } else {
            throw new InvalidInformation("Tipo de usuario incorrecto");
        }
    }
    public Iterable<Usuario> obtenerTodos() {return usuarioRepository.findAll();}

    public Usuario obtenerUnoPorCorreo(String email) {return usuarioRepository.findOneByEmail(email);}

    public Usuario obtenerUnoPorCedula(Long cedula) {return usuarioRepository.findOneByCedula(cedula);}

}
