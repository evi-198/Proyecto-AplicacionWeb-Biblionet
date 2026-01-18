package com.biblionet.service;

/*
 * Servicio de Reportes para rol Administrador 
 * -------------------------------------------------
 * Define operaciones de consulta y agregación
 * de datos para reportes del administrador.
 * No interviene en flujos operativos del sistema,
 * solo en análisis y visualización de información.
 */

import com.biblionet.dto.LibroMasPrestadoDTO;
import com.biblionet.dto.UsuarioSancionadoDTO;
import com.biblionet.model.Categoria;
import com.biblionet.model.EstadoSancion;

import java.util.List;

public interface ReporteAdminService {

    // =========================
    // Reporte: Libros más prestados
    // =========================
    /* Permite obtener el ranking de libros,
       con filtro opcional por categoría */
    List<LibroMasPrestadoDTO> obtenerLibrosMasPrestados(Integer categoriaId);

    // filtro de Lista de categorías para selección en reportes
    List<Categoria> listarCategorias();

    // =========================
    // Reporte: Usuarios sancionados
    // =========================
    // Devuelve usuarios sancionados con filtro por estado
    List<UsuarioSancionadoDTO> obtenerUsuariosSancionados(EstadoSancion estado);

    /* Conteo total de usuarios sancionados 
      (activos + inactivos) para el Dashboard */
    Long contarUsuariosSancionados();

    // =========================
    // Gráfico: Usuarios sancionados vs sin sanción
    // =========================
    // Conteo de usuarios con sanción activa entre Alumno y Docente
    Long contarUsuariosSancionadosActivos();

}
