package com.proyecto.encriptacion.repository;

import com.proyecto.encriptacion.entity.Md5Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Md5HashRepository extends JpaRepository<Md5Hash, Long> {

    Md5Hash findByRutaHashAndIdHash(String rutaHash, String idHash);

    Md5Hash findByIdHash(String idHash);

    Md5Hash findByRutaHash(String rutaHash);
}