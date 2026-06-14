package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.config.StockConfig;
import com.inventory.smart.inventory.model.NivelAlerta;
import org.springframework.stereotype.Component;

@Component
public class AlertaEstandarStrategy implements AlertaStrategy {

    @Override
    public NivelAlerta evaluar(int stock, StockConfig config) {
        if (stock < config.critico()) {
            return NivelAlerta.CRITICO;
        } else if (stock < config.minimo()) {
            return NivelAlerta.BAJO;
        }
        return NivelAlerta.NORMAL;
    }
}
