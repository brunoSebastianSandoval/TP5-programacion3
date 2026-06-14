package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.MovimientoRequest;
import com.inventory.smart.inventory.model.MovimientoInventario;
import java.util.List;

public interface MovimientoService {
    MovimientoInventario registrarMovimiento(MovimientoRequest request);
    List<MovimientoInventario> obtenerHistorialPorProducto(Long productoId);
}