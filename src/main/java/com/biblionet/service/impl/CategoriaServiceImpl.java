package com.biblionet.service.impl;

import com.biblionet.model.Categoria;
import com.biblionet.repository.CategoriaRepository;
import com.biblionet.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Implementación del servicio de Categorías.
 * Centraliza las operaciones CRUD y métricas simples
 * usadas tanto en mantenimiento como en dashboards.
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Inyección por constructor para mantener el servicio desacoplado
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Listado general de categorías
    @Override
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    // Búsqueda puntual por identificador
    @Override
    public Optional<Categoria> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    // Registro o actualización de categorías
    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Eliminación directa (uso administrativo)
    @Override
    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }

    // Conteo total de categorías para Dashboard (Admin y Bibliotecario)
    @Override
    public long contarCategorias() {
        return categoriaRepository.count();
    }

}
