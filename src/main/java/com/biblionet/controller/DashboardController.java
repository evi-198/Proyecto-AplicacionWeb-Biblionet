package com.biblionet.controller;


import com.biblionet.service.ActividadUsuarioService;
import com.biblionet.service.CategoriaService;
import com.biblionet.service.LibroService;
import com.biblionet.service.UsuarioCrudService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblionet.service.PrestamoService;
import com.biblionet.service.ReporteAdminService;
import com.biblionet.model.Usuario;
import com.biblionet.model.UsuarioCrud;
import com.biblionet.repository.UsuarioCrudRepository;

// import para Actividad Reciente de Alumno y docente
import com.biblionet.model.ActividadUsuario;

@Controller
public class DashboardController {

	// Gestión de usuarios del sistema (usado por el dashboard del Administrador)
	private final UsuarioCrudService usuarioCrudService;
 
	// Operaciones y métricas de libros (conteos para Admin y Bibliotecario)
	private final LibroService libroService;

	// Gestión de categorías de libros (clasificación y reportes)
    private final CategoriaService categoriaService;
    
    // Lógica central de préstamos (para admin y Bibliotecario)
    private final PrestamoService prestamoService; 
    
    // Generación de reportes globales del Administrador
    private final ReporteAdminService reporteAdminService;
    
    // Acceso directo a usuarios para consultas y validaciones específicas
    private final UsuarioCrudRepository usuarioCrudRepository;
    
    // Reportes operativos del Bibliotecario (devoluciones, estados, seguimiento)
    private final com.biblionet.service.ReporteBibliotecarioService reporteBibliotecarioService;
    
    // Registro y seguimiento de la actividad de usuarios (Alumno / Docente)
    private final ActividadUsuarioService actividadUsuarioService;

    
    // Inyección de dependencias por constructor
    public DashboardController(
            UsuarioCrudService usuarioCrudService,
            
            LibroService libroService,
            CategoriaService categoriaService,
            PrestamoService prestamoService,
            ReporteAdminService reporteAdminService,
            UsuarioCrudRepository usuarioCrudRepository,
            com.biblionet.service.ReporteBibliotecarioService reporteBibliotecarioService, 
            ActividadUsuarioService actividadUsuarioService 
            
    ) {
        this.usuarioCrudService = usuarioCrudService;
        this.libroService = libroService;
        this.categoriaService = categoriaService;
        this.prestamoService = prestamoService;
        this.reporteAdminService = reporteAdminService;
        this.usuarioCrudRepository = usuarioCrudRepository; 
        this.reporteBibliotecarioService = reporteBibliotecarioService; 
        this.actividadUsuarioService = actividadUsuarioService; 
      
    }

    // ==========================
    // DASHBOARD ADMINISTRADOR
    // ==========================
    @GetMapping("/dashboard/admin")
    public String dashboardAdmin(Model model) {

        // Título del panel
        model.addAttribute("titulo", "Panel Administrador");

        // Fragmento que se inyecta dentro de layout/dashboard.html
        model.addAttribute("contenido", "dashboard/dashboard-admin");

        // ==== CONTEO DE LOS NUMEROS DINAMICOS DE LOS KPIs ===========
        
        // 1.  Conteo de usuarios activos (solo el admin puede verlo)
        long usuariosActivos = usuarioCrudService.contarUsuariosActivos();
        model.addAttribute("totalUsuarios", usuariosActivos);
         
        // 2. Conteo de libros registrados
        model.addAttribute("libros", libroService.contarLibros());
        
        // 3. Conteo de categorias 
        model.addAttribute("categorias", categoriaService.contarCategorias());

        // 4. Conteo de prestamos registrados 
        model.addAttribute(
                "prestamosRegistrados",
                prestamoService.contarPrestamosRegistrados()
        );

        // 5. Conteo de los prestamos en estado Pendiente 
        model.addAttribute(
                "totalHistorialPrestamos",
                prestamoService.contarDevolucionesPendientes()
        );
        
        // 6. Conteo de Usarios sancionados ya sea activo o inactivo (solo el admin puede verlo)
        model.addAttribute(
        	    "usuariosSancionados",
        	    reporteAdminService.contarUsuariosSancionados()
        	);
        
        // ===== GRAFICOS DIDAMICOS CORRESPONDIENTES AL ADMIN ========
        
        // grafico de barras Datos: libros mas prestados   
        model.addAttribute(
        	    "librosMasPrestados",
        	    reporteAdminService.obtenerLibrosMasPrestados(null)
        	);

        // grafico circular Datos: usuarios sancionados entre Alumnos y Docentes vs sin sanción
        long totalAdmins = usuarioCrudRepository.contarPorRol("Administrador");
        long totalBibliotecarios = usuarioCrudRepository.contarPorRol("Bibliotecario");
        long alumnosDocentes = usuariosActivos - totalAdmins - totalBibliotecarios;
        
        model.addAttribute("alumnosDocentesActivos", alumnosDocentes);
                
        // Siempre se retorna el layout principal
        return "layout/dashboard";
    }

    // ==========================
    // DASHBOARD BIBLIOTECARIO
    // ==========================
    @GetMapping("/dashboard/bibliotecario")
    public String dashboardBibliotecario(Model model) {

        // Título del panel
        model.addAttribute("titulo", "Panel Bibliotecario");

        // Fragmento específico del bibliotecario
        model.addAttribute("contenido", "dashboard/dashboard-biblio");
        
       // ==== CONTEO DE LOS NUMEROS DINAMICOS DE LOS KPIs ===========

        // 1. conteo de libros registrados 
        model.addAttribute("libros", libroService.contarLibros());
        
        // 2. Conteo de categorias
        model.addAttribute("categorias", categoriaService.contarCategorias());
        
        // 3. Conteo de los prestamos en estado Pendiente 
        model.addAttribute("totalHistorialPrestamos", prestamoService.contarDevolucionesPendientes());
        
        
       // ===== GRAFICOS DIDAMICOS CORRESPONDIENTES AL BIBLIOTECARIO ========
        
        // grafico de areas Datos: préstamos por Día
        java.util.Map<String, Object> datosPrestamos = reporteBibliotecarioService.obtenerEstadisticasPrestamosDia();
        model.addAttribute("labelsDia", datosPrestamos.get("labels"));
        model.addAttribute("dataDia", datosPrestamos.get("data"));

        // grafico de barras Datos: Ejemplares por Estado
        java.util.Map<String, Object> datosEjemplares = reporteBibliotecarioService.obtenerEstadisticasEjemplaresEstado();
        model.addAttribute("labelsEstado", datosEjemplares.get("labels"));
        model.addAttribute("dataEstado", datosEjemplares.get("data"));
        
        return "layout/dashboard";
    }

    // ==========================
    // DASHBOARD DOCENTE
    // ==========================
    @GetMapping("/dashboard/docente")
    public String dashboardDocente(HttpSession session, Model model) {

    	 Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

         if (usuarioSesion == null) {
             return "redirect:/auth/login";
         }

         UsuarioCrud usuarioCrud = usuarioCrudService
                 .obtenerPorId(usuarioSesion.getId())
                 .orElse(null);

         if (usuarioCrud == null) {
             return "redirect:/auth/login";
         }
         
      // Datos para la vista
         model.addAttribute("titulo", "Panel Docente");
         model.addAttribute("contenido", "dashboard/dashboard-docente");
         
      // ==== CONTEO DE LOS NUMEROS DINAMICOS DE LOS KPIs ===========
         
         // 1. conteo de libros en estado disponible con al menos un ejemplar 
         model.addAttribute(
                 "totalLibrosDisponibles",
                 libroService.contarLibrosConEjemplaresDisponibles()
         );

         // 2. Conteo de préstamos activos (pendientes + atrasados)
         long misPrestamos = prestamoService
                 .contarPrestamosActivosPorUsuario(usuarioCrud.getId());
         model.addAttribute("totalPrestamosDocente", misPrestamos);
         
         // 3. Conteo de ultimas consultas - Solo del día actual
         long consultasHoy = actividadUsuarioService.contarConsultasDelDia(usuarioCrud.getId());
         model.addAttribute("consultasHoy", consultasHoy);

         // 4. ACTIVIDAD RECIENTE - Últimas 5 acciones
         java.util.List<ActividadUsuario> actividadReciente = 
                 actividadUsuarioService.obtenerActividadReciente(usuarioCrud.getId());
         model.addAttribute("actividadReciente", actividadReciente);
         

        return "layout/dashboard";
    }


    // ==========================
    // DASHBOARD ALUMNO
    // ==========================
    @GetMapping("/dashboard/alumno")
    public String dashboardAlumno(HttpSession session, Model model) {

        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/auth/login";
        }

        UsuarioCrud usuarioCrud = usuarioCrudService
                .obtenerPorId(usuarioSesion.getId())
                .orElse(null);

        if (usuarioCrud == null) {
            return "redirect:/auth/login";
        }

       // Datos para la vista
        model.addAttribute("titulo", "Panel Alumno");
        model.addAttribute("contenido", "dashboard/dashboard-alumno");
        
       // ==== CONTEO DE LOS NUMEROS DINAMICOS DE LOS KPIs ===========
        
      // 1. conteo de libros en estado disponible con al menos un ejemplar 
        model.addAttribute(
                "totalLibrosDisponibles",
                libroService.contarLibrosConEjemplaresDisponibles()
        );
        
        // 2. Conteo de préstamos activos (pendientes + atrasados)
        long misPrestamos = prestamoService
                .contarPrestamosActivosPorUsuario(usuarioCrud.getId());
        model.addAttribute("totalPrestamosAlumno", misPrestamos);
        
       // 3. Conteo de ultimas consultas - Solo del día actual
        long consultasHoy = actividadUsuarioService.contarConsultasDelDia(usuarioCrud.getId());
        model.addAttribute("consultasHoy", consultasHoy);

        // 4. ACTIVIDAD RECIENTE - Últimas 5 acciones
        java.util.List<ActividadUsuario> actividadReciente = 
                actividadUsuarioService.obtenerActividadReciente(usuarioCrud.getId());
        model.addAttribute("actividadReciente", actividadReciente);
        
        
        return "layout/dashboard";
    }

}
