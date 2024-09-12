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
        if (usuarioRepository.findOneByCedula(usuario.getCedula()).isPresent()) {
            throw new UsuarioYaExiste("Ya existe usuario con esa cedula");
        }
        if (usuarioRepository.findOneByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioYaExiste("Ya existe usuario con ese correo");
        }


        usuarioRepository.save(usuario);
        System.out.println("Usuario agregado exitosamente");

        System.out.println("Usuario guardado exitosamente: " + usuario.getEmail());
    }

    public boolean validarEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }


    public boolean validarDatosLogin(String email, String contrasena) {

        if (!validarEmail(email)) {
            return false;
        }
        return true;

    }

    public boolean validarDatosRegistro(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, String genero, Date fechaNacimiento) {
        // Validar cédula (solo números y longitud adecuada)
        if (String.valueOf(cedula).length() != 8) {
            return false;
        }
        // Validar email con la función previamente creada
        if (!validarEmail(email)) {
            return false;
        }
        // Validar teléfono (solo números y longitud adecuada)
        if (!telefono.matches("\\d{8,15}")) {
            return false;
        }
        // Validar fecha de nacimiento (debe ser una fecha pasada)
        Date fechaActual = new Date();
        if (fechaNacimiento.after(fechaActual)) {
            return false;
        }
        // Si todas las validaciones pasan
        return true;
    }


    public Usuario validarLogin(String email, String contrasena) throws InvalidInformation, EntidadNoExiste {
//        if (!validarDatosLogin(email, contrasena)) {
//            throw new InvalidInformation("Datos de login incorrectos");}

        Usuario usuario = usuarioRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario no existe"));
        if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
        } else {
                throw new InvalidInformation("Contraseña incorrecta");
        }

    }


    public Usuario registrarUsuario(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, String genero, Date fechaNacimiento, String tipo) throws InvalidInformation, UsuarioYaExiste {
        System.out.println("ENTRO FUNCION MGR REGISTRAR");
        if (!validarDatosRegistro(cedula, nombre, apellido, direccion, email, telefono, contrasena, genero, fechaNacimiento)) {
            throw new InvalidInformation("Datos de registro incorrectos");
        }

        System.out.println("PASO VALIDACION DATOS");
        if (tipo.equals("DEMANDANTE")) {
            System.out.println("SOY DEMANDANTE");
            Date fechaCreacion = new Date();
            UsuarioDemandante usuario = new UsuarioDemandante(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
            agregarUsuario(usuario);
            System.out.println("PASO CREACION");
            return usuario;
        } else if (tipo.equals("OFERTANTE")) {
            Date fechaCreacion = new Date();
            UsuarioOfertante usuario = new UsuarioOfertante(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
            agregarUsuario(usuario);
            return usuario;
        } else {
            throw new InvalidInformation("Tipo de usuario incorrecto");
        }
    }

    public Iterable<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUnoPorCorreo(String email) {
        try {
            return usuarioRepository.findOneByEmail(email).orElseThrow(() -> new EntidadNoExiste("Usuario no existe"));
        } catch (EntidadNoExiste e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario obtenerUnoPorCedula(Long cedula) {
        try {
            return usuarioRepository.findOneByCedula(cedula).orElseThrow(() -> new EntidadNoExiste("Usuario no existe"));
        } catch (EntidadNoExiste e) {
            throw new RuntimeException(e);
        }
    }

    public String obtenerTipoUsuario(Long id) {
        return usuarioRepository.findTipoById(id);
    }

}
