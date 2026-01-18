package com.biblionet.controller;

import com.biblionet.service.ReporteAdminService;
import com.biblionet.model.EstadoSancion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/reportes")
public class AdminReporteController {

    // Servicio centralizado para los reportes del rol Administrador
    private final ReporteAdminService reporteAdminService;

    public AdminReporteController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    // =================================================
    // REPORTE: Libros Más Prestados (rol Administrador)
    // =================================================
    // Muestra el ranking de libros más prestados, con opción de filtrar por categoría
    @GetMapping("/libros-mas-prestados")
    public String librosMasPrestados(
            @RequestParam(required = false) Integer categoriaId,
            Model model
    ) {

        // Datos principales del reporte (ranking de préstamos)
        model.addAttribute(
                "librosMasPrestados",
                reporteAdminService.obtenerLibrosMasPrestados(categoriaId)
        );

        // Catálogo de categorías para el filtro del reporte
        model.addAttribute(
                "categorias",
                reporteAdminService.listarCategorias()
        );

        // Fragmento específico que se inyecta en el layout general
        model.addAttribute("contenido", "admin/reportes/libros-mas-prestados");

        return "layout/dashboard";
    }

    // ==========================================
    // REPORTE: Usuarios Sancionados (Administrador)
    // ==========================================
    // Permite visualizar usuarios sancionados y filtrar por estado de la sanción
    @GetMapping("/usuarios-sancionados")
    public String usuariosSancionados(
            @RequestParam(required = false) EstadoSancion estado,
            Model model
    ) {

        // Lista de usuarios sancionados según el estado seleccionado
        model.addAttribute(
                "usuariosSancionados",
                reporteAdminService.obtenerUsuariosSancionados(estado)
        );

        // Estado seleccionado para mantener coherencia visual en el filtro
        model.addAttribute(
                "estadoSeleccionado",
                estado
        );

        // Fragmento correspondiente al reporte dentro del dashboard
        model.addAttribute(
                "contenido",
                "admin/reportes/usuarios-sancionados"
        );

        return "layout/dashboard";
    }

}
