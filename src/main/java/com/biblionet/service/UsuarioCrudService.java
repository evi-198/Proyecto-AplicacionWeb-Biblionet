package com.biblionet.service;

import com.biblionet.model.UsuarioCrud;

import java.util.List;
import java.util.Optional;

/*
 * Servicio principal de gestión de usuarios del sistema.
 * Se usa para lógica de negocio; no debe confundirse con la entidad Usuario (login).
 */
public interface UsuarioCrudService {

    // Listado general para mantenimiento administrativo
    List<UsuarioCrud> listarTodos();

    // Búsqueda puntual usada en edición, detalle y validaciones
    Optional<UsuarioCrud> obtenerPorId(Integer id);

    // Registro de nuevos usuarios con validaciones de negocio
    UsuarioCrud guardar(UsuarioCrud usuario) throws Exception;

    // Actualización controlada de datos del usuario
    UsuarioCrud actualizar(Integer id, UsuarioCrud usuario) throws Exception;

    // Eliminación lógica para mantener trazabilidad histórica
    void eliminarLogico(Integer id) throws Exception;

    // Filtros usados en vistas administrativas
    List<UsuarioCrud> listarPorEstado(UsuarioCrud.Estado estado);
    List<UsuarioCrud> listarPorRol(Integer rolId);

    // Eliminación física (uso excepcional: datos de prueba o errores críticos)
    void eliminarFisico(Integer id) throws Exception;

    // KPI del dashboard administrativo
    long contarUsuariosActivos();

    // Fuente de usuarios válidos para el flujo de préstamos
    // (solo Alumnos y Docentes)
    List<UsuarioCrud> listarAlumnosYDocentes();
}
