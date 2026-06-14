package com.inventory.smart.inventory.controller;

import com.inventory.smart.inventory.dto.MovimientoRequest;
import com.inventory.smart.inventory.model.MovimientoInventario;
import com.inventory.smart.inventory.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para registrar y consultar movimientos de inventario.
 */
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    // Inyección por constructor
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@Valid @RequestBody MovimientoRequest request) {
        MovimientoInventario nuevoMovimiento = movimientoService.registrarMovimiento(request);
        
        // Retorna 201 Created como exige la rúbrica del TP
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovimiento);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<List<MovimientoInventario>> obtenerHistorialPorProducto(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerHistorialPorProducto(id));
    }
}
