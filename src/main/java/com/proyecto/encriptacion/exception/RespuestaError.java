package com.proyecto.encriptacion.exception;

import java.time.LocalDateTime;

public record RespuestaError (
        int status,
        String mensaje,
        LocalDateTime timestamp
) { }