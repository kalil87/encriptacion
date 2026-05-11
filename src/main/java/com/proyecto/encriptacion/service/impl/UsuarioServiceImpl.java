package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.dto.request.UsuarioCreateRequest;
import com.proyecto.encriptacion.dto.request.UsuarioLoginRequest;
import com.proyecto.encriptacion.dto.request.UsuarioUpdateRequest;
import com.proyecto.encriptacion.dto.response.UsuarioResponse;
import com.proyecto.encriptacion.entity.Usuario;
import com.proyecto.encriptacion.exception.RecursoNoEncontradoException;
import com.proyecto.encriptacion.mapper.UsuarioMapper;
import com.proyecto.encriptacion.repository.UsuarioRepository;
import com.proyecto.encriptacion.utils.TipoHashPassword;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl {
    private final UsuarioRepository repo;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder = TipoHashPassword.BCRYPT;

    public UsuarioServiceImpl(UsuarioRepository repo, UsuarioMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public UsuarioResponse create(UsuarioCreateRequest request) {

        if (repo.findByUsuario(request.usuario()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        if (repo.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("El email ya existe");
        }

        Usuario usuario = mapper.toEntity(request);

        usuario.setPassword(encoder.encode(request.password()));

        Usuario saved = repo.save(usuario);

        return mapper.toDto(saved);
    }

    public UsuarioResponse update(Long id, UsuarioUpdateRequest request) {

        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no registrado"));

        mapper.updateEntity(request, usuario);

        if (request.password() != null) {
            usuario.setPassword(encoder.encode(request.password()));
        }

        Usuario updated = repo.save(usuario);

        return mapper.toDto(updated);
    }

    public UsuarioResponse login(UsuarioLoginRequest request) {

        Usuario usuario = findByLoginInput(request.identificador())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no registrado"));

        if (!encoder.matches(request.password(), usuario.getPassword())) {
            throw new RuntimeException("Password incorrecta");
        }

        return mapper.toDto(usuario);
    }

    private java.util.Optional<Usuario> findByLoginInput(String input) {
        return isEmail(input)
                ? repo.findByEmail(input)
                : repo.findByUsuario(input);
    }

    private boolean isEmail(String input) {
        return input != null && input.contains("@");
    }
}