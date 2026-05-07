package com.proyecto.encriptacion.dto;

public record ProductResponseDto(
        Long id,
        String nombre,
        Double precio,
        Integer strock
) { }