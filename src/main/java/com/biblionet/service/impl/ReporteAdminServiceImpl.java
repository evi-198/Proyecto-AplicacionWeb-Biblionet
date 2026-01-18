package com.biblionet.service.impl;

import com.biblionet.dto.LibroMasPrestadoDTO;
import com.biblionet.dto.UsuarioSancionadoDTO;
import com.biblionet.model.EstadoSancion;
import com.biblionet.model.Categoria;
import com.biblionet.repository.CategoriaRepository;
import com.biblionet.repository.DetallePrestamoRepository;
import com.biblionet.repository.SancionRepository;
import com.biblionet.service.ReporteAdminService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/*
 * Servicio de Reportes Administrativos
 * -------------------------------------------------
 * Centraliza los reportes estratégicos del sistema
 * orientados al rol Administrador.
 *
 * No contiene lógica operativa:
 * solo coordina consultas y métricas consolidadas.
 */
@Service
public class ReporteAdminServiceImpl implements ReporteAdminService {

    private final DetallePrestamoRepository detallePrestamoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SancionRepository sancionRepository;

    public ReporteAdminServiceImpl(
            DetallePrestamoRepository detallePrestamoRepository,
            CategoriaRepository categoriaRepository,
            SancionRepository sancionRepository
    ) {
        this.detallePrestamoRepository = detallePrestamoRepository;
        this.categoriaRepository = categoriaRepository;
        this.sancionRepository = sancionRepository;
    }

    // ===============================
    // REPORTE: Libros Más Prestados
    // ===============================

    // Reporte principal con filtro opcional por categoría
    @Override
    public List<LibroMasPrestadoDTO> obtenerLibrosMasPrestados(Integer categoriaId) {
        if (categoriaId == null) {
            return detallePrestamoRepository.obtenerLibrosMasPrestados();
        } else {
            return detallePrestamoRepository.obtenerLibrosMasPrestadosPorCategoria(categoriaId);
        }
    }

    // Soporte para filtros (select de categorías)
    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // ===============================
    // REPORTE: Usuarios Sancionados
    // ===============================

    // Listado de usuarios sancionados con filtro por estado
    @Override
    public List<UsuarioSancionadoDTO> obtenerUsuariosSancionados(EstadoSancion estado) {
        return sancionRepository.obtenerUsuariosSancionados(estado);
    }

    // Métrica global:
    // usuarios que han tenido al menos una sanción
    @Override
    public Long contarUsuariosSancionados() {
        return sancionRepository.contarUsuariosSancionados();
    }

    // Métrica operativa para gráfico:
    // usuarios con sanción activa en la fecha actual
    @Override
    public Long contarUsuariosSancionadosActivos() {
        return sancionRepository.contarUsuariosSancionadosActivos(
                EstadoSancion.activa,
                LocalDate.now()
        );
    }
}
