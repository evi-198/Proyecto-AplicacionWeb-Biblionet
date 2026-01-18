package com.biblionet.service;

/*
 * Servicio de Libro
 * -------------------------------------------------
 * Define operaciones de gestión y consulta general
 * sobre los libros del sistema.
 * Se utiliza en la administración de libros
 * y en métricas para dashboards.
 */

import com.biblionet.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    // =========================
    // Operaciones CRUD
    // =========================
    // Lista todos los libros registrados
    List<Libro> listarTodos();

    // Obtiene un libro por su identificador
    Optional<Libro> buscarPorId(Integer id);

    // Registra o actualiza un libro
    Libro guardar(Libro libro);

    // Elimina un libro por ID
    void eliminar(Integer id);

    // =========================
    // Métricas para Dashboard
    // =========================
    // Conteo total de libros registrados (Bibliotecario / Administrador)
    long contarLibros();

    // Conteo de libros que tienen al menos
    // un ejemplar disponible (Alumno / Docente)
    long contarLibrosConEjemplaresDisponibles();

}
