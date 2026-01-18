package com.biblionet.controller;

import com.biblionet.model.Usuario;
import com.biblionet.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth") 
public class LoginController {

    // Servicio encargado de la autenticación básica del usuario
    // (login manual sin Spring Security)
    @Autowired
    private UsuarioService usuarioService;

    // ==========================
    // VISTA DE LOGIN
    // ==========================
    @GetMapping("/login")
    public String loginPage() {
        // Muestra el formulario de inicio de sesión
        return "auth/login";  
    }

    // ==========================
    // PROCESO DE AUTENTICACIÓN
    // ==========================
    @PostMapping("/login")
    public String login(@RequestParam String usuarioLogin,
                        @RequestParam String clave,
                        HttpSession session,
                        Model model) {

        // Validar credenciales contra el servicio de usuarios
        Usuario usuario = usuarioService.login(usuarioLogin, clave);

        if (usuario != null) {
            // Guardar el usuario autenticado en sesión
            session.setAttribute("usuario", usuario);

            // Redirección dinámica según el rol del usuario
            String rol = usuario.getRol().getNombre();
            switch (rol) {
                case "Administrador":
                    return "redirect:/dashboard/admin";
                case "Bibliotecario":
                    return "redirect:/dashboard/bibliotecario";
                case "Docente":
                    return "redirect:/dashboard/docente";
                case "Alumno":
                    return "redirect:/dashboard/alumno";
                default:
                    // Rol no reconocido o inconsistente
                    return "redirect:/auth/login";
            }

        } else {
            // Credenciales incorrectas o usuario inactivo
            model.addAttribute("error", "Usuario o contraseña incorrectos o inactivo");
            return "auth/login";
        }
    }

    // ==========================
    // CIERRE DE SESIÓN
    // ==========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Eliminar completamente la sesión del usuario
        session.invalidate();
        return "redirect:/auth/login";
    }
}
