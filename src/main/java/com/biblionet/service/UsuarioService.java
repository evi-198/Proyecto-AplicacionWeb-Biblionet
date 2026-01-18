package com.biblionet.service;

import com.biblionet.model.Usuario;

/*
 * Servicio exclusivo para autenticación.
 * Trabaja únicamente con la entidad Usuario (login),
 * sin intervenir en la lógica de negocio del sistema.
 */
public interface UsuarioService {

    // Valida credenciales y retorna la identidad de login activa
    Usuario login(String usuarioLogin, String clave);
}
