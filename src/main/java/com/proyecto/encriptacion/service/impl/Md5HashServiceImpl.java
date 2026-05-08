package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.entity.Md5Hash;
import com.proyecto.encriptacion.repository.Md5HashRepository;
import org.springframework.stereotype.Service;

@Service
public class Md5HashServiceImpl {
    private final Md5HashRepository repository;

    public Md5HashServiceImpl(Md5HashRepository repository) {
        this.repository = repository;
    }

    public Md5Hash crearMapping(Long entidadId, String rutaReal) {

        Md5Hash hash = new Md5Hash();

        hash.setEntidadId(entidadId);
        hash.setRutaReal(rutaReal);

        String idHash = generarHash(entidadId.toString());
        String rutaHash = generarHash(rutaReal);

        hash.setIdHash(idHash);
        hash.setRutaHash(rutaHash);

        return repository.save(hash);
    }

    private String generarHash(String input) {
        return Integer.toHexString(input.hashCode());
    }
}