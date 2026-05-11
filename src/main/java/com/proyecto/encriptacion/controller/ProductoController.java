package com.proyecto.encriptacion.controller;

import com.proyecto.encriptacion.dto.ProductResponseDto;
import com.proyecto.encriptacion.dto.ProductoRequestDto;
import com.proyecto.encriptacion.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encriptacion/frutas")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto crear(@Valid @RequestBody ProductoRequestDto dto, HttpServletRequest request) {
        return productoService.crear(dto, request);
    }

    @GetMapping
    public List<ProductResponseDto> listar(@RequestParam(required = false) String nombre) {
        return productoService.listar(nombre);
    }

    @GetMapping("/{id}")
    public ProductResponseDto buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDto dto) {
        return productoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}