package com.inventory.smart.inventory.repository;

import com.inventory.smart.inventory.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación base en memoria para repositorios genéricos.
 */
public abstract class GenericInMemoryRepository<T, ID extends Number> implements IGenericRepository<T, ID> {

    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();
    protected final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    @Override
    public T save(T entity) {
        return entity; 
    }

    @Override
    public void deleteById(ID id) {
        if (!dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("No se encontró la entidad con ID: " + id);
        }
        dataStore.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }

    @Override
    public long count() {
        return dataStore.size();
    }
}