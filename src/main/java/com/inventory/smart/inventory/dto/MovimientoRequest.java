package com.inventory.smart.inventory.dto;
import com.inventory.smart.inventory.model.TipoMovimiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para registrar un nuevo movimiento de stock (entrada o salida).
 */
public record MovimientoRequest(
        
        @NotNull(message = "El ID del producto es obligatorio")
        Long productoId,

        @NotNull(message = "El tipo de movimiento (ENTRADA o SALIDA) es obligatorio")
        TipoMovimiento tipo,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad a mover debe ser mayor a 0")
        Integer cantidad,

        @NotBlank(message = "Debe proveer un motivo para el movimiento")
        String motivo
) {}
