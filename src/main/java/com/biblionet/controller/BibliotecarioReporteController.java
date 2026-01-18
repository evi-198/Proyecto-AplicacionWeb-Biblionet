package com.biblionet.controller;

import com.biblionet.service.ReporteBibliotecarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bibliotecario/reportes")
public class BibliotecarioReporteController {

    // Servicio encargado de los reportes operativos del rol Bibliotecario
    private final ReporteBibliotecarioService reporteBibliotecarioService;

    public BibliotecarioReporteController(
            ReporteBibliotecarioService reporteBibliotecarioService
    ) {
        this.reporteBibliotecarioService = reporteBibliotecarioService;
    }

    // ==============================================
    // REPORTE: Devoluciones Pendientes (Bibliotecario)
    // ==============================================
    // Muestra los préstamos que aún no han sido devueltos
    @GetMapping("/devoluciones-pendientes")
    public String verDevolucionesPendientes(Model model) {

        // Datos principales del reporte operativo
        model.addAttribute(
                "devolucionesPendientes",
                reporteBibliotecarioService.listarDevolucionesPendientes()
        );

        // Fragmento específico del reporte dentro del dashboard
        model.addAttribute(
                "contenido",
                "bibliotecario/reportes/devoluciones-pendientes"
        );

        return "layout/dashboard";
    }
}
