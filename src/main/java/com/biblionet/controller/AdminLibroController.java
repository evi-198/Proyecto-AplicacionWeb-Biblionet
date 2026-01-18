package com.biblionet.controller;

import com.biblionet.model.Libro;
import com.biblionet.service.LibroService;
import com.biblionet.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/libros")
public class AdminLibroController {

    private final LibroService libroService;
    private final CategoriaService categoriaService;

    public AdminLibroController(LibroService libroService,
                                CategoriaService categoriaService) {
        this.libroService = libroService;
        this.categoriaService = categoriaService;
    }

    // =================================================
    // LISTAR LOS LIBROS REGISTRADOS (admin)
    // =================================================
    
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("titulo", "Libros");

        // Define qué contenido se muestra dentro del dashboard principal
        model.addAttribute("contenido", "libros/lista");

        return "layout/dashboard";
    }

   // =================================================
    // FORMULARIO: NUEVO LIBRO
    // =================================================

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("libro", new Libro());
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("titulo", "Nuevo Libro");

        // Se usa el mismo endpoint para guardar libros nuevos y editados
        model.addAttribute("actionUrl", "/admin/libros/guardar");

        model.addAttribute("contenido", "libros/form");
        return "layout/dashboard";
    }

   // =================================================
    // PROCESO: GUARDAR LIBRO
    // =================================================

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Libro libro,
                          RedirectAttributes redirectAttributes) {

        // El guardado se delega al servicio para mantener la lógica centralizada
        libroService.guardar(libro);

        // Mensaje mostrado después de la redirección
        redirectAttributes.addFlashAttribute(
                "mensaje", "Libro guardado exitosamente"
        );

        // Redirección para evitar reenviar el formulario
        return "redirect:/admin/libros";
    }
  
    // =================================================
    // FORMULARIO: EDITAR LIBRO EXISTENTE
    // =================================================

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        // Si el libro no existe, se corta el flujo
        Libro libro = libroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        model.addAttribute("libro", libro);
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("titulo", "Editar Libro");

        // Se reutiliza el mismo formulario que en el registro
        model.addAttribute("actionUrl", "/admin/libros/guardar");
        model.addAttribute("listUrl", "/admin/libros");

        model.addAttribute("contenido", "libros/form");

        return "layout/dashboard";
    }

    // =================================================
    // ELIMINACIÓN DE LIBRO (admin)
    // =================================================

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id,
                           RedirectAttributes redirectAttributes) {

        // La eliminación pasa por el servicio para mantener consistencia
        libroService.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Libro eliminado exitosamente"
        );

        return "redirect:/admin/libros";
    }
}
