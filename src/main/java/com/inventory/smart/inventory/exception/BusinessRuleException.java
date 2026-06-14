package com.inventory.smart.inventory.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio del dominio.
 */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}