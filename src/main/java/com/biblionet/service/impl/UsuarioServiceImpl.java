package com.biblionet.service.impl;

import com.biblionet.model.Usuario;
import com.biblionet.repository.UsuarioRepository;
import com.biblionet.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Servicio encargado de la autenticación básica de usuarios.
 * -----------------------------------------------------------
 * Se limita exclusivamente al proceso de login y validación de estado,
 * sin participar en la lógica de negocio ni en operaciones CRUD.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- Autenticación de usuario ---
    @Override
    public Usuario login(String usuarioLogin, String clave) {

        // Solo se permite el acceso a usuarios activos
        return usuarioRepository
                .findByUsuarioLoginAndClaveAndEstado(
                        usuarioLogin,
                        clave,
                        Usuario.Estado.activo
                )
                .orElse(null);
    }
}
