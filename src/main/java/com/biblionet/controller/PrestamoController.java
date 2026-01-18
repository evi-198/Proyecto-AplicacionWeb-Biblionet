package com.biblionet.controller;

import com.biblionet.model.Ejemplar;
import com.biblionet.model.EstadoEjemplar;
import com.biblionet.service.*;
import com.biblionet.repository.EjemplarRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

// Controller para el manejo toda la lógica del préstamo 
// acceso para (Admin y Bibliotecario)

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    // Servicio principal que maneja toda la lógica del préstamo
    private final PrestamoService prestamoService;

    // Servicio para obtener usuarios válidos (alumnos y docentes)
    private final UsuarioCrudService usuarioCrudService;

    // Servicio para listar libros disponibles en el formulario
    private final LibroService libroService;

    // Repositorio usado para cargar ejemplares disponibles vía AJAX
    private final EjemplarRepository ejemplarRepository;

    public PrestamoController(
            PrestamoService prestamoService,
            UsuarioCrudService usuarioCrudService,
            LibroService libroService,
            EjemplarRepository ejemplarRepository
    ) {
        this.prestamoService = prestamoService;
        this.usuarioCrudService = usuarioCrudService;
        this.libroService = libroService;
        this.ejemplarRepository = ejemplarRepository;
    }

    // ==========================
    // ENTRADA PRINCIPAL
    // ==========================
    // Redirige directamente al formulario de nuevo préstamo
    @GetMapping
    public String inicio() {
        return "redirect:/prestamos/nuevo";
    }

    // ==========================
    // FORMULARIO DE PRÉSTAMO
    // ==========================
    @GetMapping("/nuevo")
    public String formulario(Model model) {

        // Lista de usuarios habilitados para recibir préstamos
        model.addAttribute(
                "usuarios",
                usuarioCrudService.listarAlumnosYDocentes()
        );

        // Lista de libros para el primer paso del flujo
        model.addAttribute(
                "libros",
                libroService.listarTodos()
        );

        // Fragmento que se inyecta en el layout principal
        model.addAttribute("contenido", "prestamos/form");
        return "layout/dashboard";
    }

    // ==========================
    // EJEMPLARES DISPONIBLES
    // ==========================
    // Devuelve solo ejemplares disponibles del libro seleccionado
    @GetMapping("/ejemplares-disponibles/{libroId}")
    @ResponseBody
    public List<Ejemplar> obtenerEjemplaresDisponibles(
            @PathVariable Integer libroId
    ) {
        return ejemplarRepository.findByLibro_IdAndEstado(
                libroId,
                EstadoEjemplar.disponible
        );
    }

    // ==========================
    // REGISTRO DEL PRÉSTAMO
    // ==========================
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Integer usuarioId,
            @RequestParam Integer libroId, 
            @RequestParam Integer ejemplarId,
            @RequestParam LocalDate fechaDevolucion,
            RedirectAttributes redirect
    ) {

        // ID del operador (bibliotecario)
        // ⚠️ Temporal: luego se obtendrá desde sesión
        Integer operadorId = 1;

        try {
            // Registrar préstamo validando disponibilidad y sanciones
            prestamoService.registrarPrestamo(
                    usuarioId,
                    operadorId,
                    ejemplarId,
                    fechaDevolucion
            );

            // Mensaje de éxito para la vista
            redirect.addFlashAttribute(
                    "exito",
                    "Préstamo registrado correctamente"
            );

        } catch (RuntimeException e) {

            // Mensaje de error controlado (reglas de negocio)
            redirect.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        // Volver al formulario para registrar otro préstamo
        return "redirect:/prestamos/nuevo";
    }

    // ==========================
    // HISTORIAL DE PRÉSTAMOS 
    // ==========================
    @GetMapping("/historial")
    public String historial(Model model) {

        // Listado completo del historial de préstamos
        model.addAttribute(
                "prestamos",
                prestamoService.listarHistorial()
        );

        model.addAttribute("contenido", "prestamos/historial");
        return "layout/dashboard";
    }

    // ==========================
    // DETALLE DE PRÉSTAMO
    // ==========================
    @GetMapping("/{id}/detalle")
    public String detalle(@PathVariable Integer id, Model model) {

        // Información detallada de un préstamo específico
        model.addAttribute(
                "prestamo",
                prestamoService.obtenerPorId(id)
        );

        model.addAttribute("contenido", "prestamos/detalle");
        return "layout/dashboard";
    }

}
