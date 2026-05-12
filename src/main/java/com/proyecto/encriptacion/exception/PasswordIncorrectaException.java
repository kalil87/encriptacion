package com.proyecto.encriptacion.exception;

public class PasswordIncorrectaException extends RuntimeException {

    public PasswordIncorrectaException(String mensaje) {
        super(mensaje);
    }
}