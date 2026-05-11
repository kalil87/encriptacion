package com.proyecto.encriptacion.dto.response;

public record ProductResponseDto(
        Long id,
        String nombre,
        Double precio,
        Integer strock
) { }