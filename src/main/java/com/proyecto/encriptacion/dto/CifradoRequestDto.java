package com.proyecto.encriptacion.dto;

import jakarta.validation.constraints.NotBlank;

public record CifradoRequestDto(
        @NotBlank(message = "El texto no puede ser vacio")
        String texto
) { }
