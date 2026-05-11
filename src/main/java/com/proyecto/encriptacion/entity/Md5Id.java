package com.proyecto.encriptacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "md5_ids")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Md5Id {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long entidadId;

    @Column(nullable = false)
    private String idHash;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Md5Ruta ruta;

    public Md5Id(Long entidadId, String idHash) {
        this.entidadId = entidadId;
        this.idHash = idHash;
    }
}