package com.proyecto.encriptacion.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginRequest(

        @NotBlank(message = "Usuario o email incorrecto")
        String identificador,

        @NotBlank(message = "Contraseña incorrecta")
        String password

) {}