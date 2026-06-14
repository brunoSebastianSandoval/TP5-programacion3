package com.inventory.smart.inventory.exception;

/**
 * Excepción lanzada cuando se intenta realizar una salida de inventario
 * y la cantidad solicitada supera el stock disponible.
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}