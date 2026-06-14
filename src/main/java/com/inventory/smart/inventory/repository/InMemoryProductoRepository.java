package com.inventory.smart.inventory.repository;

import com.inventory.smart.inventory.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryProductoRepository extends GenericInMemoryRepository<Producto, Long> implements ProductoRepository {

    @Override
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(idGenerator.incrementAndGet()); 
        }
        dataStore.put(producto.getId(), producto);
        return producto;
    }

    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        return dataStore.values().stream()
                .filter(p -> p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    @Override
    public List<Producto> buscarPorNombre(String texto) {
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lower))
                .toList();
    }
}