package com.proyecto.encriptacion.dto.response;

public record UsuarioResponse(
        Long id,
        String usuario,
        String email
) {}