package com.biblionet.controller;

import com.biblionet.service.ConsultaLibroService;
import com.biblionet.repository.CategoriaRepository;
import com.biblionet.repository.UsuarioCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Importaciones relacionadas a la actividad de Alumno y Docente
import com.biblionet.service.ActividadUsuarioService;
import com.biblionet.model.Usuario;
import com.biblionet.model.UsuarioCrud;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/consultas")
public class ConsultaLibroController {

    // Servicios y repositorios necesarios para la consulta de libros
    // y el registro de actividad del usuario
    private final ConsultaLibroService consultaLibroService;
    private final CategoriaRepository categoriaRepository;
    private final ActividadUsuarioService actividadUsuarioService;
    private final UsuarioCrudRepository usuarioCrudRepository;

    public ConsultaLibroController(
            ConsultaLibroService consultaLibroService,
            CategoriaRepository categoriaRepository,
            ActividadUsuarioService actividadUsuarioService,
            UsuarioCrudRepository usuarioCrudRepository
    ) {
        this.consultaLibroService = consultaLibroService;
        this.categoriaRepository = categoriaRepository;
        this.actividadUsuarioService = actividadUsuarioService;
        this.usuarioCrudRepository = usuarioCrudRepository;
    }

    // =====================================================
    // CONSULTA DE LIBROS DISPONIBLES (Alumno / Docente)
    // =====================================================
    // Permite buscar libros por texto, categoría o ambos,
    // registrando la actividad del usuario cuando corresponde
    @GetMapping("/libros")
    public String consultarLibros(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Integer categoriaId,
            HttpSession session,
            Model model
    ) {

        // Usuario autenticado obtenido desde sesión
        // (usado únicamente para registrar actividad)
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

        if (usuarioSesion != null) {

            // Se trabaja con UsuarioCrud para mantener coherencia
            // con la lógica de negocio del sistema
            UsuarioCrud usuarioCrud = usuarioCrudRepository
                    .findById(usuarioSesion.getId()).orElse(null);

            if (usuarioCrud != null) {

                // Construcción dinámica de la descripción de la consulta
                // según los filtros aplicados por el usuario
                String descripcion;

                if (texto != null && !texto.trim().isEmpty() && categoriaId != null) {
                    // Búsqueda combinada: texto + categoría
                    String nombreCategoria = categoriaRepository.findById(categoriaId)
                            .map(categoria -> categoria.getNombreCategoria())
                            .orElse("ID " + categoriaId);
                    descripcion = "Buscó: '" + texto.trim() + "' en categoría: " + nombreCategoria;

                } else if (texto != null && !texto.trim().isEmpty()) {
                    // Búsqueda solo por texto
                    descripcion = "Buscó: '" + texto.trim() + "'";

                } else if (categoriaId != null) {
                    // Búsqueda solo por categoría
                    String nombreCategoria = categoriaRepository.findById(categoriaId)
                            .map(categoria -> categoria.getNombreCategoria())
                            .orElse("ID " + categoriaId);
                    descripcion = "Buscó libros en categoría: " + nombreCategoria;

                } else {
                    // Consulta sin filtros
                    descripcion = "Consultó todos los libros disponibles";
                }

                // Registro de la actividad de consulta del usuario
                actividadUsuarioService.registrarConsulta(usuarioCrud, descripcion);
            }
        }

        // Listado de categorías para el filtro en la vista
        model.addAttribute("categorias", categoriaRepository.findAll());

        // Resultado de la consulta (filtrada o completa)
        model.addAttribute(
                "libros",
                consultaLibroService.buscarLibrosDisponibles(texto, categoriaId)
        );

        // Mantiene los valores seleccionados tras la consulta
        model.addAttribute("texto", texto);
        model.addAttribute("categoriaId", categoriaId);

        // Fragmento específico cargado en el dashboard
        model.addAttribute("contenido", "consultas/libros");

        return "layout/dashboard";
    }

}
