package com.biblionet.repository;

import com.biblionet.model.Ejemplar;
import com.biblionet.model.EstadoEjemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * Repositorio de Ejemplar
 * -------------------------------------------------
 * Centraliza consultas relacionadas con disponibilidad,
 * validaciones operativas y métricas para dashboards.
 */
public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
    
    // ====== DISPONIBILIDAD =========
    
    // Obtiene ejemplares según su estado actual (disponible, prestado, dañado)
    List<Ejemplar> findByEstado(EstadoEjemplar estado);

    // Obtiene los ejemplares DISPONIBLES de un libro específico
    List<Ejemplar> findByLibro_IdAndEstado(Integer libroId, EstadoEjemplar estado);
    
    // ======= VALIDACIONES ==========
   
    // Verifica si un libro tiene al menos un ejemplar disponible
    boolean existsByLibro_IdAndEstado(Integer libroId, EstadoEjemplar estado);
 
    // ====== CONTADORES / MÉTRICAS ======

    /* Cuenta la cantidad de ejemplares según su estado
    Usado como KPI y para gráficos en el Dashboard del Bibliotecario */
    long countByEstado(EstadoEjemplar estado);


    /* Cuenta cuántos LIBROS tienen al menos un ejemplar disponible
    KPI operativo usado en dashboards (Alumno, Docente, Bibliotecario) */
    @Query("""
    	    SELECT COUNT(DISTINCT e.libro)
    	    FROM Ejemplar e
    	    WHERE e.estado = com.biblionet.model.EstadoEjemplar.disponible
    	""")
    	long contarLibrosConEjemplarDisponible();


    /* Cuenta ejemplares de un libro específico según estado
    Usado en el flujo de Préstamos para validaciones */
    long countByLibro_IdAndEstado(Integer libroId, EstadoEjemplar estado);


    /* Obtiene el conteo de ejemplares agrupados por estado
    Usado para construir el gráfico de estados en el Dashboard del Bibliotecario */
    @Query("SELECT e.estado, COUNT(e) FROM Ejemplar e GROUP BY e.estado")
    List<Object[]> countEjemplaresPorEstado();

}

