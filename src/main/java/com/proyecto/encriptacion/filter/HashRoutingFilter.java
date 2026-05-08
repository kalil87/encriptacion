package com.proyecto.encriptacion.filter;

import com.proyecto.encriptacion.entity.Md5Hash;
import com.proyecto.encriptacion.repository.Md5HashRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class HashRoutingFilter extends OncePerRequestFilter {

    private final Md5HashRepository repository;

    public HashRoutingFilter(Md5HashRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api") || path.contains(".")) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] parts = path.substring(1).split("/");

        if (parts.length == 1) {

            String rutaHash = parts[0];

            Md5Hash mapping = repository.findByRutaHash(rutaHash);

            if (mapping == null) {
                response.setStatus(404);
                return;
            }

            String realPath = mapping.getRutaReal();

            request.getRequestDispatcher(realPath)
                    .forward(request, response);

            return;
        }

        if (parts.length == 2) {

            String rutaHash = parts[0];
            String idHash = parts[1];

            Md5Hash mapping =
                    repository.findByRutaHashAndIdHash(rutaHash, idHash);

            if (mapping == null) {
                response.setStatus(404);
                return;
            }

            String realPath =
                    mapping.getRutaReal() + "/" + mapping.getEntidadId();

            request.getRequestDispatcher(realPath)
                    .forward(request, response);

            return;
        }

        response.setStatus(404);
    }
}