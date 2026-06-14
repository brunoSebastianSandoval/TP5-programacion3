package com.inventory.smart.inventory.controller;

import com.inventory.smart.inventory.dto.AlertaStockResponse;
import com.inventory.smart.inventory.service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para consultar las alertas de inventario.
 */
@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    // Inyección por constructor
    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<AlertaStockResponse>> obtenerStockBajo() {
        return ResponseEntity.ok(alertaService.obtenerProductosConStockBajo());
    }
}