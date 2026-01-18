package com.biblionet.repository;

import com.biblionet.model.ActividadUsuario;
import com.biblionet.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Repositorio de ActividadUsuario
 * -------------------------------------------------
 * Centraliza las consultas relacionadas a la trazabilidad
 * y métricas de acciones realizadas por los usuarios.
 * Se usa principalmente para KPIs y dashboards.
 */
public interface ActividadUsuarioRepository extends JpaRepository<ActividadUsuario, Integer> {

    /*
     * KPI: Conteo de actividades de un tipo específico en el día actual.
     * Se utiliza para métricas rápidas (ej: consultas realizadas hoy).
     */
    @Query("""
        SELECT COUNT(a)
        FROM ActividadUsuario a
        WHERE a.usuario.id = :usuarioId
          AND a.tipoActividad = :tipo
          AND a.fecha BETWEEN :inicioDia AND :finDia
    """)
    long contarConsultasDelDia(
            @Param("usuarioId") Integer usuarioId,
            @Param("tipo") TipoActividad tipo,
            @Param("inicioDia") LocalDateTime inicioDia,
            @Param("finDia") LocalDateTime finDia
    );

    
     // Devuelve las últimas 5 actividades del usuario.
     // Pensado para el panel "actividad reciente" en dashboards.
    List<ActividadUsuario> findTop5ByUsuarioIdOrderByFechaDesc(Integer usuarioId);
}
