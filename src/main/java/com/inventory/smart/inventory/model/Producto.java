package com.inventory.smart.inventory.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Entidad principal que representa un producto en el inventario.
 * Utiliza AtomicInteger para garantizar thread-safety en las modificaciones de stock.
 * * @since 1.0
 */
public class Producto {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria; // Composición: Tiene una categoría, no hereda de ella.
    
    // Campo clave del TP5
    private final AtomicInteger stock;

    public Producto(Long id, String nombre, String descripcion, double precio, int stockInicial, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = new AtomicInteger(stockInicial);
    }

    // --- Getters y Setters básicos ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // --- Métodos de negocio para el Stock (Thread-Safe) ---
    public int getStock() {
        return stock.get();
    }

    public int incrementarStock(int cantidad) {
        return stock.addAndGet(cantidad);
    }

    public int decrementarStock(int cantidad) {
        // La validación de que no quede en negativo se hará en el Service
        return stock.addAndGet(-cantidad);
    }
}
