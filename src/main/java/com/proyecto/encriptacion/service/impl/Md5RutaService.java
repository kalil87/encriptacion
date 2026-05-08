package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.entity.Md5Id;
import com.proyecto.encriptacion.entity.Md5Ruta;
import com.proyecto.encriptacion.repository.Md5RutaRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class Md5RutaService {
    private final Md5RutaRepository rutaRepository;

    public Md5RutaService(Md5RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public Md5Ruta obtenerOCrearRuta(String rutaReal) {

        return rutaRepository.findByRutaReal(rutaReal)
                .orElseGet(() -> {

                    Md5Ruta ruta = new Md5Ruta();

                    ruta.setRutaReal(rutaReal);
                    ruta.setRutaHash(generarMd5(rutaReal));

                    return rutaRepository.save(ruta);
                });
    }

    public void agregarId(Md5Ruta ruta, Long entidadId) {

        boolean existe = ruta.getIds().stream()
                .anyMatch(i -> i.getEntidadId().equals(entidadId));

        if (existe) {
            return;
        }

        Md5Id md5Id = new Md5Id();

        md5Id.setEntidadId(entidadId);
        md5Id.setIdHash(generarMd5(entidadId.toString()));
        md5Id.setRuta(ruta);

        ruta.getIds().add(md5Id);

        rutaRepository.save(ruta);
    }

    private String generarMd5(String texto) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(texto.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}