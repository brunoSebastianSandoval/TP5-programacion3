package com.inventory.smart.inventory.dto;
/**
 * DTO de salida para los datos de un Producto.
 * <p>Incluye información detallada del producto junto con su categoría.</p>
 */
public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        double precio,
        int stock,
        CategoriaResponse categoria
) {}
