package com.proyecto.encriptacion.dto.response;

public record UsuarioActualizadoResponse(
        Long id,
        String usuario,
        String email,
        boolean passwordActualizado
) {}