package com.biblionet.service;

/*
 * Servicio de Ejemplar
 * -------------------------------------------------
 * Define operaciones de gestión y consulta
 * sobre los ejemplares físicos de los libros.
 * Es una pieza central en el flujo de préstamos,
 * ya que la disponibilidad se evalúa a este nivel.
 */

import com.biblionet.model.Ejemplar;
import com.biblionet.model.EstadoEjemplar;

import java.util.List;

public interface EjemplarService {

    // =========================
    // Consultas generales
    // =========================
    // Lista todos los ejemplares registrados
    List<Ejemplar> listarTodos();

    // Lista ejemplares filtrados por estado
    List<Ejemplar> listarPorEstado(EstadoEjemplar estado);

    // =========================
    // Flujo de préstamos
    // =========================
    // Obtiene los ejemplares disponibles de un libro específico
    // (punto clave antes de registrar un préstamo)
    List<Ejemplar> listarDisponiblesPorLibro(Integer libroId);

    // =========================
    // Gestión de ejemplares
    // =========================
    // Busca un ejemplar por su identificador
    Ejemplar buscarPorId(Integer id);

    // Registra o actualiza un ejemplar
    Ejemplar guardar(Ejemplar ejemplar);
}
