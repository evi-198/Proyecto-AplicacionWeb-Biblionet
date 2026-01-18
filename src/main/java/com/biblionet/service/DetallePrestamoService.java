package com.biblionet.service;

/*
 * Servicio de Detalle de Préstamo
 * -------------------------------------------------
 * Define operaciones de consulta sobre los
 * detalles asociados a un préstamo.
 * Se utiliza para visualización de información
 * y apoyo a reportes o vistas de seguimiento.
 */

import java.util.List;
import com.biblionet.model.DetallePrestamo;

public interface DetallePrestamoService {

    // =========================
    // Consulta de detalles
    // =========================
    // Obtiene los detalles asociados a un préstamo específico
    List<DetallePrestamo> listarPorPrestamo(Integer prestamoId);

}
