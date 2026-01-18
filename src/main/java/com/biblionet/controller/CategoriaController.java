package com.biblionet.controller;

import com.biblionet.model.Categoria;
import com.biblionet.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    // Servicio central para la gestión de categorías del sistema
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // ============================
    // LISTADO DE CATEGORÍAS
    // ============================
    // Muestra todas las categorías y mensajes informativos opcionales
    @GetMapping
    public String listarCategorias(
            @RequestParam(name = "mensaje", required = false) String mensaje,
            Model model
    ) {
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("contenido", "categorias/lista");
        model.addAttribute("titulo", "Categorías");
        model.addAttribute("mensaje", mensaje);
        return "layout/dashboard";
    }

    // ============================
    // FORMULARIO: NUEVA CATEGORÍA
    // ============================
    @GetMapping("/nuevo")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("contenido", "categorias/form");
        model.addAttribute("titulo", "Nueva Categoría");
        return "layout/dashboard";
    }

    // ============================
    // PROCESO: GUARDAR CATEGORÍA
    // ============================
    // Maneja tanto creación como edición según los datos recibidos
    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/categorias?mensaje=Categoria guardada correctamente";
    }

    // ============================
    // FORMULARIO: EDITAR CATEGORÍA
    // ============================
    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Integer id, Model model) {

        // Validación de existencia previa a la edición
        Categoria categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        model.addAttribute("categoria", categoria);
        model.addAttribute("contenido", "categorias/form");
        model.addAttribute("titulo", "Editar Categoría");
        return "layout/dashboard";
    }

    // ============================
    // ELIMINACIÓN DE CATEGORÍA
    // ============================
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias?mensaje=Categoria eliminada correctamente";
    }
}
