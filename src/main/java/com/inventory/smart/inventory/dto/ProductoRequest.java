package com.inventory.smart.inventory.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación y actualización de Productos.
 * <p>Implementa validaciones estrictas mediante Jakarta Bean Validation.</p>
 */
public record ProductoRequest(
        
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        Double precio,

        @NotNull(message = "El stock inicial es obligatorio")
        @PositiveOrZero(message = "El stock inicial debe ser mayor o igual a 0")
        Integer stockInicial,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId
) {}
