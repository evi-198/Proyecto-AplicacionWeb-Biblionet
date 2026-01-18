package com.biblionet.service.impl;

import com.biblionet.dto.HistorialPrestamoDTO;
import com.biblionet.repository.PrestamoRepository;
import com.biblionet.service.HistorialService;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Servicio de Historial de Préstamos
 * -------------------------------------------------
 * Encapsula el acceso al historial de préstamos
 * de un usuario (Alumno o Docente).
 *
 * Se apoya en consultas optimizadas del repositorio
 * y devuelve DTOs listos para vista.
 */
@Service
public class HistorialServiceImpl implements HistorialService {

    private final PrestamoRepository prestamoRepository;

    public HistorialServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    // Historial personal del usuario (Mi Historial)
    @Override
    public List<HistorialPrestamoDTO> listarHistorialPorUsuario(Integer usuarioId) {
        return prestamoRepository.listarHistorialPorUsuario(usuarioId);
    }
}
