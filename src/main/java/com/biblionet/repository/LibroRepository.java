package com.biblionet.repository;

import com.biblionet.model.Libro;
import com.biblionet.model.EstadoEjemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/*
 * Repositorio de Libro
 * -------------------------------------------------
 * Centraliza consultas de búsqueda y filtrado de libros
 * basadas en la disponibilidad de sus ejemplares.
 * Se utiliza principalmente en el flujo de préstamos
 * y en vistas de consulta para bibliotecario.
 */


public interface LibroRepository extends JpaRepository<Libro, Integer> {

    // Consulta base para el flujo de préstamos:
    // permite listar solo libros que tengan al menos un ejemplar en un estado específico
    @Query("""
        SELECT DISTINCT l
        FROM Libro l
        JOIN Ejemplar e ON e.libro = l
        WHERE e.estado = :estado
          AND (:texto IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%')))
          AND (:categoriaId IS NULL OR l.categoria.id = :categoriaId)
    """)
    List<Libro> buscarLibrosDisponibles(
            // Estado del ejemplar usado como filtro principal (ej: disponible)
            @Param("estado") EstadoEjemplar estado,

            // Filtro opcional por texto para búsquedas flexibles desde la vista
            @Param("texto") String texto,

            // Filtro opcional por categoría para mantener la consulta reutilizable
            @Param("categoriaId") Integer categoriaId
    );

}
