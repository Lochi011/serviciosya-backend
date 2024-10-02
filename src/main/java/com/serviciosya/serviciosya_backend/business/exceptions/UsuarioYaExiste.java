package com.serviciosya.serviciosya_backend.business.exceptions;

public class UsuarioYaExiste extends Exception {


    public UsuarioYaExiste(String message) {
        super(message);
    }

    public UsuarioYaExiste() {
    }
}