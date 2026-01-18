package com.biblionet.service;

/*
 * Servicio de Categoría
 * -------------------------------------------------
 * Define las operaciones de gestión de categorías
 * utilizadas en la administración del sistema.
 * Sirve como contrato entre controladores
 * y la capa de persistencia.
 */

import com.biblionet.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    // =========================
    // Operaciones CRUD
    // =========================
    // Lista todas las categorías registradas
    List<Categoria> listarTodos();

    // Obtiene una categoría por su identificador
    Optional<Categoria> buscarPorId(Integer id);

    // Registra o actualiza una categoría
    Categoria guardar(Categoria categoria);

    // Elimina una categoría por ID
    void eliminar(Integer id);

    // ========================
    // Métrica para Dashboard 
    // ========================
    // Conteo total de categorías en Dashboard de Admin y Bibliotecario
    long contarCategorias();
}
