package com.serviciosya.serviciosya_backend.business.exceptions;


public class EntidadNoExiste extends Exception {

    private String code; // New field for the error code

    // Constructor with an optional error code
    public EntidadNoExiste(String message) {
        this(null, message); // Call the main constructor with code set to null
    }
    public EntidadNoExiste(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
