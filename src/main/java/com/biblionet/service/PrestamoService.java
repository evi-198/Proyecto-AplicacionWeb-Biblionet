package com.biblionet.service;

/*
 * Servicio de Préstamo
 * -------------------------------------------------
 * Define las operaciones principales del flujo
 * de préstamos de la biblioteca.
 * Interviene en registros operativos, consultas
 * administrativas y métricas para dashboards
 * según el rol del usuario.
 */

import com.biblionet.model.Prestamo;
import java.util.List;
import java.time.LocalDate;

public interface PrestamoService {

    // =========================
    // Flujo de registro de préstamo
    // =========================
    /* Registra un préstamo validando usuario,
       operador y disponibilidad del ejemplar */
    Prestamo registrarPrestamo(
            Integer usuarioId,
            Integer operadorId,
            Integer ejemplarId,
            LocalDate fechaDevolucion
    );

    // =========================
    // Consultas administrativas
    // =========================
    // Historial completo de préstamos rol (Administrador / Bibliotecario)
    List<Prestamo> listarHistorial();

    // Detalle de un préstamo específico rol (Administrador / Bibliotecario)
    Prestamo obtenerPorId(Integer id);

    // =========================
    // Métricas para Dashboard
    // =========================
    // Total de préstamos registrados rol (Administrador / Bibliotecario)
    long contarPrestamosRegistrados();

    // Total de devoluciones pendientes rol (Administrador / Bibliotecario)
    long contarDevolucionesPendientes();

    // =========================
    // Métrica personal
    // =========================
    // Conteo de préstamos activos del usuario rol (Alumno / Docente)
    long contarPrestamosActivosPorUsuario(Integer usuarioId);

}
