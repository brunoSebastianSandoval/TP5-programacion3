package com.inventory.smart.inventory.service;

import com.inventory.smart.inventory.dto.CategoriaRequest;
import com.inventory.smart.inventory.dto.CategoriaResponse;
import java.util.List;
    /* interfaz de categoria  */
public interface CategoriaService {
    List<CategoriaResponse> findAll();
    CategoriaResponse findById(Long id);
    CategoriaResponse crear(CategoriaRequest request);
    CategoriaResponse actualizar(Long id, CategoriaRequest request);
    void eliminar(Long id);
}