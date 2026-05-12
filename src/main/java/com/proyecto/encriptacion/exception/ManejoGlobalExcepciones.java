package com.proyecto.encriptacion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejoGlobalExcepciones {

    /**
     * Maneja cuando un recurso no existe en la base de datos.
     * Retorna 404 NOT FOUND con mensaje personalizado.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> manejarNotFound(RecursoNoEncontradoException ex) {

        RespuestaError error = new RespuestaError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja credenciales inválidas.
     * Retorna 401 UNAUTHORIZED.
     */
    @ExceptionHandler(PasswordIncorrectaException.class)
    public ResponseEntity<RespuestaError> manejarPasswordIncorrecta(PasswordIncorrectaException ex) {

        RespuestaError error = new RespuestaError(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja errores de validación de @Valid en request body.
     * Retorna 400 BAD REQUEST con detalles de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> manejarValidaciones(MethodArgumentNotValidException ex) {

        String errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.joining(". "));

        RespuestaError error = new RespuestaError(
                HttpStatus.BAD_REQUEST.value(),
                errores,
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores inesperados no controlados.
     * Retorna 500 INTERNAL SERVER ERROR con mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarErroresGenerales(Exception ex) {

        RespuestaError error = new RespuestaError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}