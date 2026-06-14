package com.inventory.smart.inventory.service;
import com.inventory.smart.inventory.dto.ProductoRequest;
import com.inventory.smart.inventory.dto.ProductoResponse;
import java.util.List;

/**
 * Servicio encargado de la gestión de productos del inventario.
 *
 * <p>Proporciona operaciones CRUD y búsqueda. Aplica las reglas de negocio definidas
 * para la creación y modificación de productos.</p>
 *
 * @since 1.0
 */
public interface ProductoService {

    List<ProductoResponse> findAll();

    ProductoResponse findById(Long id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);

    List<ProductoResponse> buscarPorNombre(String texto);
}
