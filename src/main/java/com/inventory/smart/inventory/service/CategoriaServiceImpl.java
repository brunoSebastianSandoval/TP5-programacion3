package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.CategoriaRequest;
import com.inventory.smart.inventory.dto.CategoriaResponse;
import com.inventory.smart.inventory.exception.BusinessRuleException;
import com.inventory.smart.inventory.exception.ResourceNotFoundException;
import com.inventory.smart.inventory.model.Categoria;
import com.inventory.smart.inventory.model.Producto;
import com.inventory.smart.inventory.repository.CategoriaRepository;
import com.inventory.smart.inventory.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaResponse(c.getId(), c.getNombre(), c.getDescripcion()))
                .toList();
    }

    @Override
    public CategoriaResponse findById(Long id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return new CategoriaResponse(c.getId(), c.getNombre(), c.getDescripcion());
    }

    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria nueva = new Categoria(null, request.nombre(), request.descripcion());
        Categoria guardada = categoriaRepository.save(nueva);
        return new CategoriaResponse(guardada.getId(), guardada.getNombre(), guardada.getDescripcion());
    }

    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        Categoria actualizada = categoriaRepository.save(existente);
        return new CategoriaResponse(actualizada.getId(), actualizada.getNombre(), actualizada.getDescripcion());
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        // Validación de la regla de negocio
        List<Producto> productosAsociados = productoRepository.findByCategoria(id);
        if (!productosAsociados.isEmpty()) {
            throw new BusinessRuleException("No se puede eliminar la categoría porque tiene productos asociados.");
        }
        categoriaRepository.deleteById(id);
    }
}