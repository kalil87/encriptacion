package com.proyecto.encriptacion.repository;

import com.proyecto.encriptacion.entity.Md5Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Md5IdRepository extends JpaRepository<Md5Id, Long> {

    Optional<Md5Id> findFirstByIdHash(String hash);

    Optional<Md5Id> findByIdHash(String idHash);

    Optional<Md5Id> findByEntidadId(Long entidadId);
}