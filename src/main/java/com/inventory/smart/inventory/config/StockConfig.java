package com.inventory.smart.inventory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mapea las propiedades de application.yml con el prefijo "inventario.stock".
 */
@ConfigurationProperties(prefix = "inventario.stock")
public record StockConfig(int minimo, int critico) {
}