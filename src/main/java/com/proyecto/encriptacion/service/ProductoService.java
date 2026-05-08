package com.proyecto.encriptacion.service;

import com.proyecto.encriptacion.dto.ProductResponseDto;
import com.proyecto.encriptacion.dto.ProductoRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductoService {

    ProductResponseDto crear(ProductoRequestDto dto, HttpServletRequest request);

    List<ProductResponseDto> listar(String nombre);

    ProductResponseDto buscarPorId(Long id);

    ProductResponseDto actualizar(Long id, ProductoRequestDto dto);

    void eliminar(Long id);

    List<ProductResponseDto> buscarPorNombre(String nombre);
}