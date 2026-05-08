package com.proyecto.encriptacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "md5_rutas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Md5Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rutaReal;

    @Column(nullable = false, unique = true)
    private String rutaHash;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Md5Id> ids = new ArrayList<>();
}