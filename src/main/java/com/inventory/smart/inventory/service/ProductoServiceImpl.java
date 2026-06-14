package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.CategoriaResponse;
import com.inventory.smart.inventory.dto.ProductoRequest;
import com.inventory.smart.inventory.dto.ProductoResponse;
import com.inventory.smart.inventory.exception.ResourceNotFoundException;
import com.inventory.smart.inventory.model.Categoria;
import com.inventory.smart.inventory.model.Producto;
import com.inventory.smart.inventory.repository.CategoriaRepository;
import com.inventory.smart.inventory.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Inyección de dependencias por constructor (Requerimiento estricto del TP)
    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ProductoResponse> findAll() {
        return productoRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con ID: " + id));
        return mapToResponse(producto);
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        // 1. Validar que la categoría exista
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.categoriaId()));

        // 2. Construir la entidad de dominio
        Producto nuevoProducto = new Producto(
                null, // El ID se genera dinámicamente en el Repository
                request.nombre(),
                request.descripcion(),
                request.precio(),
                request.stockInicial(),
                categoria
        );

        // 3. Guardar en memoria
        Producto productoGuardado = productoRepository.save(nuevoProducto);

        // 4. Retornar el DTO de salida
        return mapToResponse(productoGuardado);
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con ID: " + id));

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.categoriaId()));

        // Actualizamos los campos mutables
        productoExistente.setNombre(request.nombre());
        productoExistente.setDescripcion(request.descripcion());
        productoExistente.setPrecio(request.precio());
        productoExistente.setCategoria(categoria);
        // NOTA: El stock NO se actualiza por acá, se hace a través de Movimientos.

        Producto productoActualizado = productoRepository.save(productoExistente);
        return mapToResponse(productoActualizado);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoResponse> buscarPorNombre(String texto) {
        return productoRepository.buscarPorNombre(texto).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // --- Métodos Privados Auxiliares ---

    /**
     * Convierte una entidad Producto a un DTO ProductoResponse.
     * Esto evita exponer la entidad real al controlador.
     */
    private ProductoResponse mapToResponse(Producto producto) {
        CategoriaResponse categoriaResponse = new CategoriaResponse(
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre(),
                producto.getCategoria().getDescripcion()
        );

        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                categoriaResponse
        );
    }
}
