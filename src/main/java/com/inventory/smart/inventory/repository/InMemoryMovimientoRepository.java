package com.inventory.smart.inventory.repository;

import com.inventory.smart.inventory.model.MovimientoInventario;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMovimientoRepository extends GenericInMemoryRepository<MovimientoInventario, Long> implements MovimientoRepository {

    @Override
    public MovimientoInventario save(MovimientoInventario movimiento) {
        if (movimiento.getId() == null) {
            movimiento.setId(idGenerator.incrementAndGet());
        }
        dataStore.put(movimiento.getId(), movimiento);
        return movimiento;
    }
}