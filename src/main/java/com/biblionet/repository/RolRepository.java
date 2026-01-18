package com.biblionet.repository;

/*
 * Repositorio de Rol
 * -------------------------------------------------
 * Gestiona el acceso a los roles del sistema.
 * Se utiliza como soporte en la gestión de usuarios
 * y en validaciones de asignación de permisos.
 */

import com.biblionet.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    // Repositorio base:
    // no define consultas personalizadas porque los roles
    // se manejan como datos estructurales del sistema

}
