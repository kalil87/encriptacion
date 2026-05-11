package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.dto.request.UsuarioCreateRequest;
import com.proyecto.encriptacion.dto.request.UsuarioLoginRequest;
import com.proyecto.encriptacion.dto.request.UsuarioUpdateRequest;
import com.proyecto.encriptacion.dto.response.UsuarioActualizadoResponse;
import com.proyecto.encriptacion.dto.response.UsuarioResponse;
import com.proyecto.encriptacion.entity.Md5Ruta;
import com.proyecto.encriptacion.entity.Usuario;
import com.proyecto.encriptacion.exception.RecursoNoEncontradoException;
import com.proyecto.encriptacion.mapper.UsuarioMapper;
import com.proyecto.encriptacion.repository.UsuarioRepository;
import com.proyecto.encriptacion.utils.TipoHashPassword;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl {
    private final UsuarioRepository repo;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder = TipoHashPassword.BCRYPT;
    private final Md5RutaService md5RutaService;

    public UsuarioServiceImpl(UsuarioRepository repo, UsuarioMapper mapper, Md5RutaService md5RutaService) {
        this.repo = repo;
        this.mapper = mapper;
        this.md5RutaService = md5RutaService;
    }

    public UsuarioResponse create(UsuarioCreateRequest dto, HttpServletRequest request) {

        if (repo.findByUsuario(dto.usuario()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        if (repo.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("El email ya existe");
        }

        Usuario usuario = mapper.toEntity(dto);
        usuario.setPassword(encoder.encode(dto.password()));
        Usuario guardado = repo.save(usuario);

        String rutaReal = request.getRequestURI().replaceAll("/$", "");
        Md5Ruta ruta = md5RutaService.obtenerOCrearRuta(rutaReal);

        md5RutaService.agregarId(ruta, guardado.getId());

        return mapper.toDto(guardado);
    }

    public UsuarioActualizadoResponse update(Long id, UsuarioUpdateRequest request) {

        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no registrado"));

        mapper.updateEntity(request, usuario);

        boolean passwordActualizado = false;

        if (request.password() != null) {
            usuario.setPassword(encoder.encode(request.password()));
            passwordActualizado = true;
        }

        Usuario updated = repo.save(usuario);

        return mapper.toDto(updated, passwordActualizado);
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