package com.inventory.smart.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50)
        String nombre,

        @Size(max = 200)
        String descripcion
) {}