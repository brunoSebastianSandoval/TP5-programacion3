package com.inventory.smart.inventory.exception;
/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado.
 * Devuelve un status HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}