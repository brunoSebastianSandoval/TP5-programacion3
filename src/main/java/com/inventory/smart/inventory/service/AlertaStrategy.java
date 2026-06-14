package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.config.StockConfig;
import com.inventory.smart.inventory.model.NivelAlerta;

/**
 * Patrón Strategy para determinar el nivel de alerta de un producto.
 */
public interface AlertaStrategy {
    NivelAlerta evaluar(int stock, StockConfig config);
}