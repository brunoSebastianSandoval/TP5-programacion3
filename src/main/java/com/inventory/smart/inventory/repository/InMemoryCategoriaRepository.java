package com.inventory.smart.inventory.repository;

import com.inventory.smart.inventory.model.Categoria;
import org.springframework.stereotype.Repository;

/**
 * Implementación concreta en memoria para el repositorio de Categorías.
 */
@Repository
public class InMemoryCategoriaRepository extends GenericInMemoryRepository<Categoria, Long> implements CategoriaRepository {

    @Override
    public Categoria save(Categoria categoria) {
        // Generamos el ID si es una categoría nueva
        if (categoria.getId() == null) {
            categoria.setId(idGenerator.incrementAndGet());
        }
        // Guardamos en el ConcurrentHashMap heredado
        dataStore.put(categoria.getId(), categoria);
        return categoria;
    }
}