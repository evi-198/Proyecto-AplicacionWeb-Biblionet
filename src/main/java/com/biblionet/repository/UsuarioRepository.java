package com.biblionet.repository;

/*
 * Repositorio de Usuario (Autenticación)
 * -------------------------------------------------
 * Gestiona exclusivamente la validación de credenciales
 * para el acceso al sistema.
 * Esta entidad NO se usa en la lógica de negocio,
 * solo en el proceso de login/autenticación.
 */

import com.biblionet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // =========================================
    // Autenticación de usuarios
    // =========================================
    // Se utiliza únicamente en el proceso de login
    // para validar credenciales y estado del usuario
    Optional<Usuario> findByUsuarioLoginAndClaveAndEstado(
            String usuarioLogin,
            String clave,
            Usuario.Estado estado
    );

}
