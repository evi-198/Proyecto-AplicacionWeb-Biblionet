package com.biblionet.service;

import com.biblionet.dto.DevolucionPendienteDTO;
import java.util.List;
import java.util.Map;

/*
 * Reportes operativos exclusivos del rol Bibliotecario.
 * Centraliza consultas que no son CRUD y alimentan vistas y gráficos del dashboard.
 */
public interface ReporteBibliotecarioService {

    /* Lista de devoluciones aún no entregadas.
       Se usa en la vista operativa de control de préstamos. */
    List<DevolucionPendienteDTO> listarDevolucionesPendientes();
    
    /* Estadísticas de préstamos agrupados por día.
       Alimenta el gráfico "Préstamos por Día" del dashboard del bibliotecario.*/
    Map<String, Object> obtenerEstadisticasPrestamosDia();

    /* Estadísticas de ejemplares por estado (disponible, prestado, dañado).
       Permite visualizar rápidamente el estado del inventario. */
    Map<String, Object> obtenerEstadisticasEjemplaresEstado();
}
