package com.biblionet.service.impl;

import com.biblionet.repository.DetallePrestamoRepository;
import com.biblionet.service.DetallePrestamoService;
import com.biblionet.model.DetallePrestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Servicio de Detalle de Préstamo
 * -------------------------------------------------
 * Se encarga de recuperar los detalles asociados
 * a un préstamo específico.
 *
 * Usado principalmente para vistas de detalle
 * (Admin / Bibliotecario).
 */
@Service
public class DetallePrestamoServiceImpl
        implements DetallePrestamoService {

    @Autowired
    private DetallePrestamoRepository repository;

    // Obtiene todos los detalles asociados a un préstamo
    @Override
    public List<DetallePrestamo> listarPorPrestamo(Integer prestamoId) {
        return repository.findByPrestamoId(prestamoId);
    }
}
