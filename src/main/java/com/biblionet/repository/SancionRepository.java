package com.biblionet.repository;

import com.biblionet.model.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.biblionet.dto.UsuarioSancionadoDTO;
import com.biblionet.model.EstadoSancion;

import java.time.LocalDate;
import java.util.List;

/*
 * Repositorio de Sanción
 * -------------------------------------------------
 * Centraliza validaciones de sanciones activas
 * y consultas para reportes administrativos.
 * Interviene en flujos de préstamo, control de usuarios
 * y generación de métricas para dashboards.
 */
public interface SancionRepository extends JpaRepository<Sancion, Integer> {

    // =========================================
    // Validación de sanción activa por usuario
    // =========================================
    /* Se utiliza antes de permitir operaciones como préstamos
     para verificar si el usuario tiene una sanción vigente */
    @Query("""
        SELECT COUNT(s) > 0
        FROM Sancion s
        WHERE s.usuario.id = :usuarioId
          AND s.estado = com.biblionet.model.EstadoSancion.activa     
          AND s.fechaInicio <= :fechaActual
          AND (s.fechaFin IS NULL OR s.fechaFin >= :fechaActual)
    """)
    boolean existsSancionActiva(
            @Param("usuarioId") Integer usuarioId, 
            @Param("fechaActual") LocalDate fechaActual
    );

    // =========================================
    // Reporte: Usuarios sancionados (Admin)
    // =========================================
    /* Permite listar usuarios sancionados con filtro opcional
       por estado de la sanción (activa / inactiva) */
    @Query("""
        SELECT new com.biblionet.dto.UsuarioSancionadoDTO(
            u.id,
            u.nombre,
            u.apellido,
            u.dni,
            u.usuarioLogin,
            r.nombre,
            s.motivo,
            s.fechaInicio,
            s.fechaFin,
            s.estado
        )
        FROM Sancion s
        JOIN s.usuario u
        JOIN u.rol r
        WHERE (:estado IS NULL OR s.estado = :estado)
        ORDER BY s.fechaInicio DESC
    """)
    List<UsuarioSancionadoDTO> obtenerUsuariosSancionados(
            @Param("estado") EstadoSancion estado
    );

    // =========================================
    // Métrica general: usuarios sancionados
    // =========================================
    /* Conteo total de usuarios que han tenido
       al menos una sanción registrada */
    @Query("""
        SELECT COUNT(DISTINCT s.usuario.id)
        FROM Sancion s
    """)
    Long contarUsuariosSancionados();

    // ======================================================
    // Gráfico: Usuarios sancionados vs sin sanción (Dashboard Admin)
    // ======================================================
    // Cuenta usuarios con sanción vigente según fecha actual
    @Query("""
        SELECT COUNT(DISTINCT s.usuario.id)
        FROM Sancion s
        WHERE s.estado = :estado
          AND s.fechaInicio <= :fechaActual
          AND (s.fechaFin IS NULL OR s.fechaFin >= :fechaActual)
    """)
    Long contarUsuariosSancionadosActivos(
            @Param("estado") EstadoSancion estado,
            @Param("fechaActual") LocalDate fechaActual
    );

}
