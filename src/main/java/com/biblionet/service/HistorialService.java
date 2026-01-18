package com.biblionet.service;

/*
 * Servicio de Historial de Préstamos
 * -------------------------------------------------
 * Define operaciones de consulta del historial
 * de préstamos por usuario.
 * Se utiliza en vistas personales de alumnos
 * y docentes para seguimiento de sus préstamos.
 */

import com.biblionet.dto.HistorialPrestamoDTO;
import java.util.List;

public interface HistorialService {

    // =========================
    // Consulta de historial
    // =========================
    // Devuelve el historial de préstamos del usuario
    List<HistorialPrestamoDTO> listarHistorialPorUsuario(Integer usuarioId);

}
