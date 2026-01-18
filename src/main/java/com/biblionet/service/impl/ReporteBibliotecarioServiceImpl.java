package com.biblionet.service.impl;

import com.biblionet.dto.DevolucionPendienteDTO;
import com.biblionet.repository.PrestamoRepository;
import com.biblionet.service.ReporteBibliotecarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*
 * Servicio de Reportes del Bibliotecario
 * -------------------------------------------------
 * Centraliza reportes operativos y métricas visuales
 * usadas en el Dashboard del Bibliotecario.
 *
 * Su función es transformar resultados crudos
 * en estructuras listas para gráficos.
 */
@Service
public class ReporteBibliotecarioServiceImpl
        implements ReporteBibliotecarioService {

    private final PrestamoRepository prestamoRepository;
    private final com.biblionet.repository.EjemplarRepository ejemplarRepository;

    public ReporteBibliotecarioServiceImpl(
            PrestamoRepository prestamoRepository,
            com.biblionet.repository.EjemplarRepository ejemplarRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    // Listado operativo de devoluciones pendientes
    // (uso diario del bibliotecario)
    @Transactional(readOnly = true)
    @Override
    public List<DevolucionPendienteDTO> listarDevolucionesPendientes() {
        return prestamoRepository.listarDevolucionesPendientes();
    }

    // ===============================
    // DASHBOARD: Préstamos por Día
    // ===============================

    // Prepara datos agregados para gráfico de líneas
    @Override
    public Map<String, Object> obtenerEstadisticasPrestamosDia() {
        List<Object[]> resultados = prestamoRepository.countPrestamosPorDia();

        List<String> labels = resultados.stream()
                .map(r -> r[0].toString())
                .toList();

        List<Long> data = resultados.stream()
                .map(r -> (Long) r[1])
                .toList();

        Map<String, Object> mapa = new java.util.HashMap<>();
        mapa.put("labels", labels);
        mapa.put("data", data);

        return mapa;
    }

    // ===============================
    // DASHBOARD: Ejemplares por Estado
    // ===============================

    // Prepara datos para gráfico de barras
    @Override
    public Map<String, Object> obtenerEstadisticasEjemplaresEstado() {
        List<Object[]> resultados = ejemplarRepository.countEjemplaresPorEstado();

        List<String> labels = resultados.stream()
                .map(r -> r[0].toString())
                .toList();

        List<Long> data = resultados.stream()
                .map(r -> (Long) r[1])
                .toList();

        Map<String, Object> mapa = new java.util.HashMap<>();
        mapa.put("labels", labels);
        mapa.put("data", data);

        return mapa;
    }
}
