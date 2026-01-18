package com.biblionet.controller;

import com.biblionet.model.Usuario;
import com.biblionet.service.HistorialService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Controller para ver el historial personalizado de pretamos
// acceso solo a Alumno y Docente 

@Controller
@RequestMapping("/consultas")
public class HistorialController {

    // Servicio encargado de la lógica del historial de consultas/préstamos
    // (usado solo por Alumno y Docente)
    private final HistorialService historialService;

    // Inyección del servicio por constructor
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    // ===============================
    // HISTORIAL PERSONAL DEL USUARIO
    // ===============================
    @GetMapping("/mi-historial")
    public String verMiHistorial(HttpSession session, Model model) {

        // Obtener el usuario autenticado desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Validar sesión activa antes de continuar
        if (usuario == null) {
            // Si no hay usuario en sesión, se redirige al login
            return "redirect:/auth/login";
        }
        
        // Se utiliza el ID del usuario logueado para filtrar su historial
        Integer usuarioId = usuario.getId();

        // Cargar el historial asociado únicamente a este usuario
        model.addAttribute(
            "historial",
            historialService.listarHistorialPorUsuario(usuarioId)
        );

        // Fragmento que se inyecta dentro del layout principal
        model.addAttribute("contenido", "consultas/mi-historial");

        return "layout/dashboard";
    }
}
