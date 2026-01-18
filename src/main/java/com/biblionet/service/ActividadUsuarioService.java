package com.biblionet.service;

import com.biblionet.model.ActividadUsuario;
import com.biblionet.model.TipoActividad;
import com.biblionet.model.UsuarioCrud;
import com.biblionet.repository.ActividadUsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/*
 * Servicio de Actividad de Usuario (ALUMNO Y DOCENTE)
 * -------------------------------------------------
 * Gestiona el registro y consulta de actividades
 * realizadas por los usuarios dentro del sistema.
 * Se utiliza para métricas personales, dashboards
 * y seguimiento de uso.
 */

@Service
public class ActividadUsuarioService {

    private final ActividadUsuarioRepository actividadUsuarioRepository;

    // Inyección del repositorio encargado de persistir
    // y consultar la actividad de los usuarios
    public ActividadUsuarioService(ActividadUsuarioRepository actividadUsuarioRepository) {
        this.actividadUsuarioRepository = actividadUsuarioRepository;
    }

    // =========================================
    // Registro de actividad: consultas
    // =========================================
    /* Se invoca cuando un usuario realiza
       una consulta dentro del sistema */
    public void registrarConsulta(UsuarioCrud usuario, String descripcion) {

        ActividadUsuario actividad = new ActividadUsuario(
                usuario,
                TipoActividad.consulta,
                descripcion
        );

        actividadUsuarioRepository.save(actividad);
    }

    // =========================================
    // Métrica diaria de consultas (HOY)
    // =========================================
    /* Permite contar cuántas consultas realizó
       un usuario en el día actual */
    public long contarConsultasDelDia(Integer usuarioId) {

        LocalDate hoy = LocalDate.now();

        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);

        return actividadUsuarioRepository.contarConsultasDelDia(
                usuarioId,
                TipoActividad.consulta,
                inicioDia,
                finDia
        );
    }

    // =========================================
    // Actividad reciente del usuario
    // =========================================
    /* Devuelve las últimas 5 actividades registradas
       para mostrar en dashboards o vistas personales */
    public List<ActividadUsuario> obtenerActividadReciente(Integer usuarioId) {
        return actividadUsuarioRepository.findTop5ByUsuarioIdOrderByFechaDesc(usuarioId);
    }
}
