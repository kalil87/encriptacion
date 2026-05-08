package com.proyecto.encriptacion.filter;

import com.proyecto.encriptacion.entity.Md5Id;
import com.proyecto.encriptacion.entity.Md5Ruta;
import com.proyecto.encriptacion.repository.Md5IdRepository;
import com.proyecto.encriptacion.repository.Md5RutaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class HashRoutingFilter extends OncePerRequestFilter {
    private final Md5RutaRepository rutaRepository;
    private final Md5IdRepository idRepository;

    public HashRoutingFilter(Md5RutaRepository rutaRepository, Md5IdRepository idRepository) {
        this.rutaRepository = rutaRepository;
        this.idRepository = idRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        String[] partes = uri.split("/");


        if (partes.length >= 2) {

            String rutaHash = partes[1];

            Optional<Md5Ruta> rutaOpt = rutaRepository.findByRutaHash(rutaHash);

            if (rutaOpt.isPresent()) {

                Md5Ruta ruta = rutaOpt.get();


                if (partes.length == 2) {

                    RequestDispatcher dispatcher = request.getRequestDispatcher(ruta.getRutaReal());

                    dispatcher.forward(request, response);

                    return;
                }


                if (partes.length == 3) {

                    String idHash = partes[2];

                    Optional<Md5Id> idOpt = idRepository.findByIdHash(idHash);

                    if (idOpt.isPresent()) {

                        Md5Id md5Id = idOpt.get();

                        String nuevaRuta = ruta.getRutaReal() + "/" + md5Id.getEntidadId();

                        RequestDispatcher dispatcher = request.getRequestDispatcher(nuevaRuta);

                        dispatcher.forward(request, response);

                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}