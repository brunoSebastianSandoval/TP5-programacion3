package com.inventory.smart.inventory.model;

import java.time.LocalDateTime;

public class MovimientoInventario {
private Long id;
    private Long productoId;
    private TipoMovimiento tipo;
    private int cantidad;
    private int stockResultante;
    private String motivo;
    private LocalDateTime fecha;

    public MovimientoInventario(Long id, Long productoId, TipoMovimiento tipo, int cantidad, int stockResultante, String motivo) {
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.stockResultante = stockResultante;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now(); // Se sella la fecha automáticamente al crearlo
    }

    // Getters (Normalmente un movimiento no debería tener setters públicos 
    // para mantener la inmutabilidad del historial, salvo el ID si se genera después)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductoId() { return productoId; }
    public TipoMovimiento getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
    public int getStockResultante() { return stockResultante; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFecha() { return fecha; }
}
