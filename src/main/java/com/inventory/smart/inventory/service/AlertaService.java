package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.AlertaStockResponse;
import java.util.List;

public interface AlertaService {
    List<AlertaStockResponse> obtenerProductosConStockBajo();
}