package com.proyecto.encriptacion.controller;

import com.proyecto.encriptacion.dto.request.UsuarioCreateRequest;
import com.proyecto.encriptacion.dto.request.UsuarioLoginRequest;
import com.proyecto.encriptacion.dto.request.UsuarioUpdateRequest;
import com.proyecto.encriptacion.dto.response.UsuarioResponse;
import com.proyecto.encriptacion.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encriptacion/usuarios")
public class UsuarioController {
    private final UsuarioServiceImpl service;

    public UsuarioController(UsuarioServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody @Valid UsuarioCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(@RequestBody @Valid UsuarioLoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}