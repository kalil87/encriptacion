package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.entity.Md5Id;
import com.proyecto.encriptacion.entity.Md5Ruta;
import com.proyecto.encriptacion.repository.Md5RutaRepository;
import com.proyecto.encriptacion.utils.SistemaNumeracion;
import com.proyecto.encriptacion.utils.VariantesHash;
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

                    Md5Ruta ruta = new Md5Ruta(rutaReal, generarMd5(rutaReal));

                    return rutaRepository.save(ruta);
                });
    }

    public void agregarId(Md5Ruta ruta, Long entidadId) {

        boolean existe = ruta.getIds()
                .stream()
                .anyMatch(i -> i.getEntidadId().equals(entidadId));

        if (existe) {
            return;
        }

        Md5Id md5Id = new Md5Id(entidadId, generarMd5(entidadId.toString()));

        ruta.agregarId(md5Id);

        rutaRepository.save(ruta);
    }

    private String generarMd5(String texto) {

        try {
            MessageDigest md5 = MessageDigest.getInstance(VariantesHash.SHA_256);

            byte[] bytes = md5.digest(texto.getBytes());

            return SistemaNumeracion.hexadecimal(bytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}