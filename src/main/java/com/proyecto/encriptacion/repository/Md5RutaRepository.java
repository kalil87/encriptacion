package com.proyecto.encriptacion.repository;

import com.proyecto.encriptacion.entity.Md5Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Md5RutaRepository extends JpaRepository<Md5Ruta, Long> {

    Optional<Md5Ruta> findByRutaHash(String rutaHash);

    Optional<Md5Ruta> findByRutaReal(String rutaReal);
}