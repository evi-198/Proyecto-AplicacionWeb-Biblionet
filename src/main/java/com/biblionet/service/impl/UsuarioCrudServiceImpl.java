package com.biblionet.service.impl;

import com.biblionet.model.UsuarioCrud;
import com.biblionet.repository.RolRepository;
import com.biblionet.repository.UsuarioCrudRepository;
import com.biblionet.service.UsuarioCrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Servicio encargado de la gestión completa de usuarios del sistema.
 * -------------------------------------------------------------------
 * Centraliza las validaciones de integridad, reglas de negocio y operaciones
 * CRUD sobre la entidad UsuarioCrud, manteniendo consistencia con roles y estados.
 */
@Service
public class UsuarioCrudServiceImpl implements UsuarioCrudService {

    // Repositorio principal para la gestión de usuarios del sistema
    private final UsuarioCrudRepository usuarioCrudRepository;

    // Repositorio usado para validar la existencia de roles
    private final RolRepository rolRepository;

    public UsuarioCrudServiceImpl(UsuarioCrudRepository usuarioCrudRepository,
                                  RolRepository rolRepository) {
        this.usuarioCrudRepository = usuarioCrudRepository;
        this.rolRepository = rolRepository;
    }

    //============================
    // Listado general de usuarios (uso administrativo)
    //============================
    @Override
    public List<UsuarioCrud> listarTodos() {
        return usuarioCrudRepository.findAll();
    }

    // Obtención de usuario por ID
    @Override
    public Optional<UsuarioCrud> obtenerPorId(Integer id) {
        return usuarioCrudRepository.findById(id);
    }
    
    //=================================
    // Registro de un nuevo usuario con validaciones de negocio
    //=================================
    @Override
    public UsuarioCrud guardar(UsuarioCrud usuario) throws Exception {

        // Regla: el DNI debe ser único en el sistema
        if (usuarioCrudRepository.existsByDni(usuario.getDni())) {
            throw new Exception("El DNI ya está registrado.");
        }

        // Regla: el usuarioLogin no puede repetirse
        if (usuarioCrudRepository.existsByUsuarioLogin(usuario.getUsuarioLogin())) {
            throw new Exception("El usuarioLogin ya está registrado.");
        }

        // Regla: el rol asignado debe existir
        if (usuario.getRol() == null || !rolRepository.existsById(usuario.getRol().getId())) {
            throw new Exception("El rol especificado no existe.");
        }

        // Estado por defecto al crear un usuario
        if (usuario.getEstado() == null) {
            usuario.setEstado(UsuarioCrud.Estado.activo);
        }

        return usuarioCrudRepository.save(usuario);
    }

    //==============================
    // Actualización de datos de un usuario existente
    //==============================
    @Override
    public UsuarioCrud actualizar(Integer id, UsuarioCrud usuario) throws Exception {

        UsuarioCrud existente = usuarioCrudRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        // Validación de DNI evitando colisión con otros usuarios
        if (!existente.getDni().equals(usuario.getDni()) &&
                usuarioCrudRepository.existsByDni(usuario.getDni())) {
            throw new Exception("El DNI ya está registrado por otro usuario.");
        }

        // Validación de usuarioLogin evitando duplicados
        if (!existente.getUsuarioLogin().equals(usuario.getUsuarioLogin()) &&
                usuarioCrudRepository.existsByUsuarioLogin(usuario.getUsuarioLogin())) {
            throw new Exception("El usuarioLogin ya está registrado por otro usuario.");
        }

        // Regla: el rol debe ser válido
        if (usuario.getRol() == null || !rolRepository.existsById(usuario.getRol().getId())) {
            throw new Exception("El rol especificado no existe.");
        }

        // Sincronización de datos editables
        existente.setNombre(usuario.getNombre());
        existente.setApellido(usuario.getApellido());
        existente.setDni(usuario.getDni());
        existente.setDireccion(usuario.getDireccion());
        existente.setTelefono(usuario.getTelefono());
        existente.setCorreo(usuario.getCorreo());
        existente.setUsuarioLogin(usuario.getUsuarioLogin());
        existente.setClave(usuario.getClave());
        existente.setEstado(usuario.getEstado());
        existente.setRol(usuario.getRol());
        existente.setOrigen(usuario.getOrigen());

        return usuarioCrudRepository.save(existente);
    }
    
     //==================================
    // Eliminación lógica: se desactiva el usuario sin borrar historial
    //===================================
    @Override
    public void eliminarLogico(Integer id) throws Exception {
        UsuarioCrud usuario = usuarioCrudRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        usuario.setEstado(UsuarioCrud.Estado.inactivo);
        usuarioCrudRepository.save(usuario);
    }

    //===================================
    // Eliminación física: borra completamente el registro (uso controlado)
    //===================================
    @Override
    public void eliminarFisico(Integer id) throws Exception {

        // Validación previa de existencia
        if (!usuarioCrudRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado.");
        }

        usuarioCrudRepository.deleteById(id);
    }

    // Listado filtrado por estado del usuario
    @Override
    public List<UsuarioCrud> listarPorEstado(UsuarioCrud.Estado estado) {
        return usuarioCrudRepository.findByEstado(estado);
    }

    // Listado de usuarios según rol
    @Override
    public List<UsuarioCrud> listarPorRol(Integer rolId) {
        return usuarioCrudRepository.findByRol_Id(rolId);
    }

    // KPI: conteo de usuarios activos para dashboards admin
    @Override
    public long contarUsuariosActivos() {
        return usuarioCrudRepository.countByEstado(UsuarioCrud.Estado.activo);
    }

    // Usuarios habilitados para el flujo de préstamos
    @Override
    public List<UsuarioCrud> listarAlumnosYDocentes() {

        // Regla de negocio:
        // solo los usuarios finales pueden solicitar un préstamo
        return usuarioCrudRepository.findByRol_IdIn(
                List.of(3, 4) // IDs de roles: Alumno y Docente
        );
    }

}
