package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.MovimientoRequest;
import com.inventory.smart.inventory.exception.InsufficientStockException;
import com.inventory.smart.inventory.exception.ResourceNotFoundException;
import com.inventory.smart.inventory.model.MovimientoInventario;
import com.inventory.smart.inventory.model.Producto;
import com.inventory.smart.inventory.model.TipoMovimiento;
import com.inventory.smart.inventory.repository.MovimientoRepository; // Hay que crear esta interfaz
import com.inventory.smart.inventory.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final ProductoRepository productoRepository;
    // Asumimos que vas a crear MovimientoRepository extendiendo IGenericRepository
    private final MovimientoRepository movimientoRepository; 

    public MovimientoServiceImpl(ProductoRepository productoRepository, MovimientoRepository movimientoRepository) {
        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    public MovimientoInventario registrarMovimiento(MovimientoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con ID: " + request.productoId()));

        int stockResultante;

        if (request.tipo() == TipoMovimiento.ENTRADA) {
            stockResultante = producto.incrementarStock(request.cantidad());
        } else {
            // Es SALIDA: Validamos que haya stock suficiente ANTES de restar
            if (producto.getStock() < request.cantidad()) {
                throw new InsufficientStockException("No se pueden retirar " + request.cantidad() + 
                        " unidades. Stock disponible: " + producto.getStock());
            }
            stockResultante = producto.decrementarStock(request.cantidad());
        }

        // Guardamos el producto con el stock actualizado
        productoRepository.save(producto);

        // Generamos el registro histórico inmutable
        MovimientoInventario movimiento = new MovimientoInventario(
                null, // El ID lo genera el repositorio
                producto.getId(),
                request.tipo(),
                request.cantidad(),
                stockResultante,
                request.motivo()
        );

        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<MovimientoInventario> obtenerHistorialPorProducto(Long productoId) {
        // Validación de que el producto existe
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        
        // Iteramos todos los movimientos y filtramos (O(n) como pide el TP)
        return movimientoRepository.findAll().stream()
                .filter(m -> m.getProductoId().equals(productoId))
                .toList();
    }
}