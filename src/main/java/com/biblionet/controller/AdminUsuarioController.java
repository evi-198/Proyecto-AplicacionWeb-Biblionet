package com.biblionet.controller;

import com.biblionet.model.UsuarioCrud;
import com.biblionet.repository.RolRepository;
import com.biblionet.service.UsuarioCrudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para administrar los Usuarios.
 * Gestionado solo por el Administrador.
 */

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    // Servicios principales para la gestión de usuarios del rol Administrador
    private final UsuarioCrudService usuarioCrudService;
    private final RolRepository rolRepository;

    public AdminUsuarioController(UsuarioCrudService usuarioCrudService,
                                  RolRepository rolRepository) {
        this.usuarioCrudService = usuarioCrudService;
        this.rolRepository = rolRepository;
    }

    // ======================================
    // LISTADO GENERAL DE USUARIOS (Admin)
    // ======================================
    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("titulo", "Usuarios");
        model.addAttribute("usuarios", usuarioCrudService.listarTodos());

        // Vista de listado inyectada en el layout principal
        model.addAttribute("contenido", "admin/usuarios/lista");
        return "layout/dashboard";
    }

    // ======================================
    // FORMULARIO: NUEVO USUARIO (Admin)
    // ======================================
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", new UsuarioCrud());
        model.addAttribute("roles", rolRepository.findAll());

        // Reutiliza el mismo formulario para creación
        model.addAttribute("contenido", "admin/usuarios/form");
        return "layout/dashboard";
    }

    // ======================================
    // PROCESO: GUARDAR NUEVO USUARIO
    // ======================================
    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute("usuario") UsuarioCrud usuario,
            BindingResult result,
            Model model
    ) {
        try {
            usuarioCrudService.guardar(usuario);
        } catch (Exception e) {
            // En caso de error, se retorna al formulario manteniendo datos y roles
            model.addAttribute("titulo", "Nuevo Usuario");
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolRepository.findAll());

            model.addAttribute("contenido", "admin/usuarios/form");
            return "layout/dashboard";
        }

        return "redirect:/admin/usuarios?success";
    }

    // ======================================
    // FORMULARIO: EDITAR USUARIO EXISTENTE
    // ======================================
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {

        Optional<UsuarioCrud> usuarioOp = usuarioCrudService.obtenerPorId(id);

        // Validación de existencia antes de mostrar el formulario
        if (usuarioOp.isEmpty()) {
            return "redirect:/admin/usuarios?notfound";
        }

        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("usuario", usuarioOp.get());
        model.addAttribute("roles", rolRepository.findAll());

        // Se reutiliza el formulario de creación
        model.addAttribute("contenido", "admin/usuarios/form");
        return "layout/dashboard";
    }

    // ======================================
    // PROCESO: ACTUALIZAR USUARIO
    // ======================================
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(
            @PathVariable Integer id,
            @ModelAttribute("usuario") UsuarioCrud usuario,
            Model model
    ) {
        try {
            usuarioCrudService.actualizar(id, usuario);
        } catch (Exception e) {
            // Manejo de error manteniendo coherencia visual del formulario
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolRepository.findAll());

            model.addAttribute("contenido", "admin/usuarios/form");
            return "layout/dashboard";
        }

        return "redirect:/admin/usuarios?updated";
    }

    // ======================================
    // VISTA: DETALLE DE USUARIO
    // ======================================
    @GetMapping("/detalle/{id}")
    public String detalleUsuario(@PathVariable Integer id, Model model) {

        Optional<UsuarioCrud> usuarioOp = usuarioCrudService.obtenerPorId(id);

        // Validación de existencia antes de mostrar el detalle
        if (usuarioOp.isEmpty()) {
            return "redirect:/admin/usuarios?notfound";
        }

        model.addAttribute("titulo", "Detalle Usuario");
        model.addAttribute("usuario", usuarioOp.get());

        model.addAttribute("contenido", "admin/usuarios/detalle");
        return "layout/dashboard";
    }

    // ======================================
    // ELIMINACIÓN FÍSICA DEFINITIVA (Admin)
    // ======================================
    // Acción irreversible usada solo a nivel administrativo
    @PostMapping("/eliminar/{id}")
    public String eliminarFisicoUsuario(@PathVariable Integer id) {
        try {
            usuarioCrudService.eliminarFisico(id);
        } catch (Exception e) {
            return "redirect:/admin/usuarios?error";
        }
        return "redirect:/admin/usuarios?deleted";
    }

}
