package com.biblionet.controller;

import com.biblionet.model.Libro;
import com.biblionet.service.LibroService;
import com.biblionet.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bibliotecario/libros")
public class BibliotecarioLibroController {

    // Servicios base para la gestión de libros por parte del Bibliotecario
    private final LibroService libroService;
    private final CategoriaService categoriaService;

    public BibliotecarioLibroController(LibroService libroService,
                                        CategoriaService categoriaService) {
        this.libroService = libroService;
        this.categoriaService = categoriaService;
    }

    // ======================================
    // LISTADO DE LIBROS (Bibliotecario)
    // ======================================
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("titulo", "Libros");

        // Vista de listado cargada dentro del dashboard
        model.addAttribute("contenido", "libros/lista");
        return "layout/dashboard";
    }

    // ======================================
    // FORMULARIO: NUEVO LIBRO
    // ======================================
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("titulo", "Nuevo Libro");

        // URL de acción reutilizada para crear y editar
        model.addAttribute("actionUrl", "/bibliotecario/libros/guardar");
        model.addAttribute("contenido", "libros/form");
        return "layout/dashboard";
    }

    // ======================================
    // PROCESO: GUARDAR LIBRO
    // ======================================
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
        libroService.guardar(libro);

        // Mensaje informativo mostrado tras la operación
        redirectAttributes.addFlashAttribute("mensaje", "Libro guardado exitosamente");
        return "redirect:/bibliotecario/libros";
    }

    // ======================================
    // FORMULARIO: EDITAR LIBRO EXISTENTE
    // ======================================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        // Carga condicional del libro a editar
        libroService.buscarPorId(id).ifPresent(libro ->
                model.addAttribute("libro", libro)
        );

        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("titulo", "Editar Libro");

        // Se reutiliza el mismo formulario de creación
        model.addAttribute("actionUrl", "/bibliotecario/libros/guardar");
        model.addAttribute("contenido", "libros/form");
        return "layout/dashboard";
    }

    // ======================================
    // ELIMINACIÓN DE LIBRO (Bibliotecario)
    // ======================================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        libroService.eliminar(id);

        // Confirmación visual posterior a la eliminación
        redirectAttributes.addFlashAttribute("mensaje", "Libro eliminado exitosamente");
        return "redirect:/bibliotecario/libros";
    }
}
