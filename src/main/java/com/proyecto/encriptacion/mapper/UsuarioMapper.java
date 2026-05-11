package com.proyecto.encriptacion.mapper;

import com.proyecto.encriptacion.dto.request.UsuarioCreateRequest;
import com.proyecto.encriptacion.dto.request.UsuarioUpdateRequest;
import com.proyecto.encriptacion.dto.response.UsuarioActualizadoResponse;
import com.proyecto.encriptacion.dto.response.UsuarioResponse;
import com.proyecto.encriptacion.entity.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioActualizadoResponse toDto(Usuario usuario, boolean passwordActualizado);

    UsuarioResponse toDto(Usuario usuario);
    Usuario toEntity(UsuarioCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateEntity(UsuarioUpdateRequest request, @MappingTarget Usuario usuario);
}