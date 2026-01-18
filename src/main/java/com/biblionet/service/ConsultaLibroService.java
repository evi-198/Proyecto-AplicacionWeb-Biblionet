package com.biblionet.service;

/*
 * Servicio de Consulta de Libros para Alumno y Docente
 * -------------------------------------------------
 * Define operaciones de búsqueda de libros
 * basadas en la disponibilidad de ejemplares.
 */

import com.biblionet.model.Libro;

import java.util.List;

public interface ConsultaLibroService {

    // =========================================
    // Búsqueda de libros disponibles
    // =========================================
    // Permite filtrar por texto y categoría,
    // retornando solo libros con ejemplares disponibles
    List<Libro> buscarLibrosDisponibles(String texto, Integer categoriaId);

}
