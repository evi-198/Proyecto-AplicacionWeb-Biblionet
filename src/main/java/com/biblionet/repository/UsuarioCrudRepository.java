package com.biblionet.repository;

import com.biblionet.model.UsuarioCrud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Repositorio de Usuario (UsuarioCrud)
 * -------------------------------------------------
 * Centraliza validaciones, búsquedas y métricas
 * relacionadas con los usuarios del sistema.
 * Interviene en flujos de préstamos, sanciones,
 * gestión administrativa y dashboards.
 */
public interface UsuarioCrudRepository extends JpaRepository<UsuarioCrud, Integer> {

    // =========================
    // Validaciones básicas
    // =========================
    // Usadas para evitar duplicados al registrar o actualizar usuarios
    boolean existsByDni(String dni);
    boolean existsByUsuarioLogin(String usuarioLogin);

    // =========================
    // Búsquedas por estado
    // =========================
    // Permite listar usuarios según su estado (activo / inactivo)
    List<UsuarioCrud> findByEstado(UsuarioCrud.Estado estado);

    // =========================
    // Búsquedas por rol
    // =========================
    // Usado en vistas administrativas y asignaciones por rol
    List<UsuarioCrud> findByRol_Id(Integer rolId);

    // =========================
    // Métricas generales
    // =========================
    // Conteo de usuarios por estado para dashboards
    long countByEstado(UsuarioCrud.Estado estado);

    // =========================
    // PRÉSTAMOS
    // =========================
    /*
     * Obtiene usuarios que pueden recibir préstamos:
     * - Estado ACTIVO
     * - Rol ALUMNO o DOCENTE
     *
     * Se excluyen administradores y bibliotecarios
     */
    @Query("""
        SELECT u
        FROM UsuarioCrud u
        WHERE u.estado = 'activo'
          AND u.rol.nombre IN ('Alumno', 'Docente')
    """)
    List<UsuarioCrud> findUsuariosPrestables();

    /* Variante usada cuando los roles se controlan por ID
       (útil para mantener flexibilidad en servicios) */
    List<UsuarioCrud> findByRol_IdIn(List<Integer> roles);

    // ==================================================
    // Gráfico: Usuarios sancionados vs sin sanción
    // ==================================================
    /* Conteo de usuarios activos por rol
       (el cálculo final del gráfico se realiza fuera del repositorio) */
    @Query("""
        SELECT COUNT(u) 
        FROM UsuarioCrud u 
        WHERE u.estado = 'activo' 
          AND u.rol.nombre = :rolNombre
    """)
    Long contarPorRol(@Param("rolNombre") String rolNombre);

}
