package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.config.StockConfig;
import com.inventory.smart.inventory.dto.AlertaStockResponse;
import com.inventory.smart.inventory.model.NivelAlerta;
import com.inventory.smart.inventory.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final AlertaStrategy alertaStrategy;
    private final StockConfig stockConfig;

    // Inyección por constructor
    public AlertaServiceImpl(ProductoRepository productoRepository, AlertaStrategy alertaStrategy, StockConfig stockConfig) {
        this.productoRepository = productoRepository;
        this.alertaStrategy = alertaStrategy;
        this.stockConfig = stockConfig;
    }

    @Override
    public List<AlertaStockResponse> obtenerProductosConStockBajo() {
        return productoRepository.findAll().stream()
                .map(producto -> {
                    // Delegamos la decisión a la estrategia
                    NivelAlerta nivel = alertaStrategy.evaluar(producto.getStock(), stockConfig);
                    return new AlertaStockResponse(
                            producto.getId(),
                            producto.getNombre(),
                            producto.getStock(),
                            nivel
                    );
                })
                .filter(response -> response.nivelAlerta() != NivelAlerta.NORMAL) // Omitimos los que están bien
                .toList();
    }
}