package com.inventory.smart.inventory.dto;

import com.inventory.smart.inventory.model.NivelAlerta;

public record AlertaStockResponse(
        Long productoId,
        String nombreProducto,
        int stockActual,
        NivelAlerta nivelAlerta
) {}