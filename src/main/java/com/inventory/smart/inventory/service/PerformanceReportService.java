package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.model.Categoria;
import com.inventory.smart.inventory.model.Producto;
import com.inventory.smart.inventory.repository.CategoriaRepository;
import com.inventory.smart.inventory.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PerformanceReportService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public PerformanceReportService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Map<String, Object> generarReporte() {
        int[] tamanios = {1000, 10000, 100000};
        Map<String, Object> reporteTotal = new HashMap<>();

        for (int n : tamanios) {
            prepararDatos(n);

            Map<String, Long> mediciones = new HashMap<>();
            
            // Medición: O(n) Búsqueda lineal manual
            long inicio = System.nanoTime();
            productoRepository.findAll();
            mediciones.put("GET /api/productos (O(n))", System.nanoTime() - inicio);

            // Medición: O(1) Búsqueda por Hash
            long idPrueba = n / 2L; 
            inicio = System.nanoTime();
            productoRepository.findById(idPrueba);
            mediciones.put("GET /api/productos/{id} (O(1))", System.nanoTime() - inicio);

            reporteTotal.put(n + " registros", mediciones);
        }

        // Limpieza de memoria
        productoRepository.findAll().forEach(p -> productoRepository.deleteById(p.getId()));
        categoriaRepository.findAll().forEach(c -> categoriaRepository.deleteById(c.getId()));

        return reporteTotal;
    }

    private void prepararDatos(int n) {
        // Limpiamos repositorios
        productoRepository.findAll().forEach(p -> productoRepository.deleteById(p.getId()));
        categoriaRepository.findAll().forEach(c -> categoriaRepository.deleteById(c.getId()));

        // Creamos una categoría base
        Categoria cat = categoriaRepository.save(new Categoria(null, "TestCat", "Cat de prueba"));

        // Insertamos 'n' registros
        for (long i = 1; i <= n; i++) {
            productoRepository.save(new Producto(null, "Prod " + i, "Desc", 100.0, 10, cat));
        }
        
        // Warm-up de la JVM
        productoRepository.findById(1L);
    }
}