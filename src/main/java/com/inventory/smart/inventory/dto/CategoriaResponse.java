package com.inventory.smart.inventory.dto;
/**
 * DTO de salida para los datos de una Categoría.
 */
public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion
) {}
