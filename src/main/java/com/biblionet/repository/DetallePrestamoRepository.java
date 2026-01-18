package com.biblionet.repository;

import com.biblionet.model.DetallePrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblionet.dto.LibroMasPrestadoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Repositorio de DetallePrestamo
 * -------------------------------------------------
 * Centraliza el acceso a los detalles de cada préstamo.
 * Desde aquí se construyen consultas históricas y reportes
 * basados en la relación Préstamo → Ejemplar → Libro.
 */
public interface DetallePrestamoRepository
        extends JpaRepository<DetallePrestamo, Integer> {

    /*
     * Obtiene todos los detalles asociados a un préstamo específico.
     * Se usa para vistas de historial o detalle del préstamo.
     */
    List<DetallePrestamo> findByPrestamoId(Integer prestamoId);

    /*
     * Reporte: Libros más prestados (sin filtro de categoría)
     * -------------------------------------------------
     * Agrupa por libro y categoría, y cuenta cuántas veces
     * aparece en los detalles de préstamo.
     * Devuelve un DTO optimizado para reportes.
     */
    @Query("""
        SELECT new com.biblionet.dto.LibroMasPrestadoDTO(
            l.id,
            l.titulo,
            c.nombreCategoria,
            COUNT(dp)
        )
        FROM DetallePrestamo dp
        JOIN dp.ejemplar e
        JOIN e.libro l
        JOIN l.categoria c
        GROUP BY l.id, l.titulo, c.nombreCategoria
        ORDER BY COUNT(dp) DESC
    """)
    List<LibroMasPrestadoDTO> obtenerLibrosMasPrestados();

    /*
     * Reporte: Libros más prestados por categoría
     * -------------------------------------------------
     * Misma lógica del reporte general, pero filtrando
     * por una categoría específica seleccionada.
     */    
    @Query("""
        SELECT new com.biblionet.dto.LibroMasPrestadoDTO(
            l.id,
            l.titulo,
            c.nombreCategoria,
            COUNT(dp)
        )
        FROM DetallePrestamo dp
        JOIN dp.ejemplar e
        JOIN e.libro l
        JOIN l.categoria c
        WHERE c.id = :categoriaId
        GROUP BY l.id, l.titulo, c.nombreCategoria
        ORDER BY COUNT(dp) DESC
    """)
    List<LibroMasPrestadoDTO> obtenerLibrosMasPrestadosPorCategoria(
        @Param("categoriaId") Integer categoriaId
    );
}
