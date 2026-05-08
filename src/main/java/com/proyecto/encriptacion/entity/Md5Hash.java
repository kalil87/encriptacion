package com.proyecto.encriptacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "md5_hash")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Md5Hash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long entidadId;

    @Column(nullable = false)
    private String rutaReal;

    @Column(nullable = false, unique = true)
    private String idHash;

    @Column(nullable = false)
    private String rutaHash;
}