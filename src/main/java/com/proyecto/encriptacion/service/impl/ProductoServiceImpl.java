package com.proyecto.encriptacion.service.impl;

import com.proyecto.encriptacion.dto.ProductResponseDto;
import com.proyecto.encriptacion.dto.ProductoRequestDto;
import com.proyecto.encriptacion.entity.Md5Ruta;
import com.proyecto.encriptacion.entity.Producto;
import com.proyecto.encriptacion.exception.RecursoNoEncontradoException;
import com.proyecto.encriptacion.mapper.ProductoMapper;
import com.proyecto.encriptacion.repository.ProductoRepository;
import com.proyecto.encriptacion.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    private ProductoRepository productoRepository;
    private final Md5RutaService md5RutaService;

    public ProductoServiceImpl(ProductoRepository repo, Md5RutaService md5RutaService) {
        productoRepository = repo;
        this.md5RutaService = md5RutaService;
    }

    @Override
    public ProductResponseDto crear(ProductoRequestDto dto, HttpServletRequest request) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto guardado = productoRepository.save(producto);

        String rutaReal = request.getRequestURI().replaceAll("/$", "");
        Md5Ruta ruta = md5RutaService.obtenerOCrearRuta(rutaReal);

        md5RutaService.agregarId(ruta, guardado.getId());

        return ProductoMapper.toResponseDto(guardado);
    }

    @Override
    public List<ProductResponseDto> listar(String nombre) {
        if (nombre == null) {
            return productoRepository.findAll()
                    .stream()
                    .map(ProductoMapper::toResponseDto)
                    .toList();
        } else {
            return buscarPorNombre(nombre);
        }
    }

    @Override
    public ProductResponseDto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toResponseDto)
                .orElseThrow(()-> new RecursoNoEncontradoException("No se encontro el producto " + id));
    }

    @Override
    public ProductResponseDto actualizar(Long id, ProductoRequestDto dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("No se encontro el producto " + id));

        ProductoMapper.updateEntity(producto, dto);
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toResponseDto(guardado);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("No se encontro el producto " + id));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductResponseDto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(ProductoMapper::toResponseDto)
                .toList();
    }
}