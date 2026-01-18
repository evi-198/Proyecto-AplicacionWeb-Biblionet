package com.biblionet.repository;

import com.biblionet.model.EstadoPrestamo;
import com.biblionet.dto.DevolucionPendienteDTO;
import com.biblionet.dto.HistorialPrestamoDTO;
import com.biblionet.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Repositorio de Préstamo
 * -------------------------------------------------
 * Centraliza consultas operativas, validaciones de negocio
 * y generación de datos para reportes y dashboards.
 * Interviene en flujos de préstamo
 * y visualización de historial por usuario.
 */
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    // ================================
    // Validaciones previas al préstamo
    // ================================
    // Se usa para verificar si un usuario ya tiene préstamos activos
    // antes de permitir registrar uno nuevo
    @Query("""
        SELECT COUNT(p)
        FROM Prestamo p
        WHERE p.usuario.id = :usuarioId
          AND p.estado IN (
              com.biblionet.model.EstadoPrestamo.pendiente,
              com.biblionet.model.EstadoPrestamo.atrasado
          )
    """)
    long contarPrestamosActivos(@Param("usuarioId") Integer usuarioId);

    // ================================
    // Métricas generales del sistema
    // ================================
    // Total de préstamos registrados
    long count();

    // Conteo de préstamos por estado (usado en dashboards y KPIs)
    long countByEstado(EstadoPrestamo estado);

    // =========================================
    // Consulta "Mi Historial" (Alumno / Docente)
    // =========================================
    // Devuelve el historial de préstamos del usuario autenticado,
    // ordenado del más reciente al más antiguo
    @Query("""
        SELECT new com.biblionet.dto.HistorialPrestamoDTO(
            l.titulo,
            e.id,
            p.fechaPrestamo,
            p.fechaDevolucion,
            p.estado
        )
        FROM Prestamo p
        JOIN p.ejemplar e
        JOIN e.libro l
        WHERE p.usuario.id = :usuarioId
        ORDER BY p.fechaPrestamo DESC
    """)
    List<HistorialPrestamoDTO> listarHistorialPorUsuario(
            @Param("usuarioId") Integer usuarioId
    );

    // ==============================================
    // Reporte de Devoluciones Pendientes (Bibliotecario)
    // ==============================================
    // Permite al bibliotecario visualizar préstamos que aún no han sido devueltos
    @Query("""
        SELECT new com.biblionet.dto.DevolucionPendienteDTO(
            l.titulo,
            e.id,
            CONCAT(u.nombre, ' ', u.apellido),
            p.fechaPrestamo,
            e.ubicacion,
            p.estado
        )
        FROM Prestamo p
        JOIN p.ejemplar e
        JOIN e.libro l
        JOIN p.usuario u
        WHERE p.estado = com.biblionet.model.EstadoPrestamo.pendiente
        ORDER BY p.fechaPrestamo ASC
    """)
    List<DevolucionPendienteDTO> listarDevolucionesPendientes();

    // =================================================
    // Gráfico: Préstamos por Día (Dashboard Bibliotecario)
    // =================================================
    // Agrupa los préstamos por fecha para alimentar el line chart
    @Query("""
        SELECT p.fechaPrestamo, COUNT(p)
        FROM Prestamo p
        GROUP BY p.fechaPrestamo
        ORDER BY p.fechaPrestamo ASC
    """)
    List<Object[]> countPrestamosPorDia();

    // ==================================================
    // Conteo de Préstamos Activos (Alumno / Docente)
    // ==================================================
    // Usado para mostrar indicadores personales en el dashboard
    @Query("""
        SELECT COUNT(p)
        FROM Prestamo p
        WHERE p.usuario.id = :usuarioId
          AND p.estado IN (
              com.biblionet.model.EstadoPrestamo.pendiente,
              com.biblionet.model.EstadoPrestamo.atrasado
          )
    """)
    long countPrestamosActivosPorUsuario(@Param("usuarioId") Integer usuarioId);

}
