package com.biblionet.service.impl;

import com.biblionet.model.*;
import com.biblionet.repository.*;
import com.biblionet.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/*
 * Servicio de Préstamos
 * -------------------------------------------------
 * Implementa el flujo completo de registro de préstamos,
 * aplicando todas las validaciones operativas del sistema.
 *
 * Es el núcleo funcional de Biblionet:
 * conecta usuarios, ejemplares, sanciones y reportes.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioCrudRepository usuarioCrudRepository;
    private final EjemplarRepository ejemplarRepository;
    private final SancionRepository sancionRepository;
    private final DetallePrestamoRepository detallePrestamoRepository;

    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepository,
            UsuarioCrudRepository usuarioCrudRepository,
            EjemplarRepository ejemplarRepository,
            SancionRepository sancionRepository,
            DetallePrestamoRepository detallePrestamoRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioCrudRepository = usuarioCrudRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.sancionRepository = sancionRepository;
        this.detallePrestamoRepository = detallePrestamoRepository;
    }

    /*
     * Registro de préstamo
     * -------------------------------------------------
     * Flujo obligatorio:
     * - Validaciones de usuario, sanciones y límites
     * - Verificación de disponibilidad del ejemplar
     * - Registro del préstamo y su detalle
     * - Actualización del estado del ejemplar
     */
    @Override
    public Prestamo registrarPrestamo(
            Integer usuarioId,
            Integer operadorId,
            Integer ejemplarId,
            LocalDate fechaDevolucion
    ) {

        // 1️. Recuperación de entidades base
        UsuarioCrud usuario = usuarioCrudRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioCrud operador = usuarioCrudRepository.findById(operadorId)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

        LocalDate hoy = LocalDate.now();

        // 2️. Validación de fecha de devolución
        if (fechaDevolucion == null || !fechaDevolucion.isAfter(hoy)) {
            throw new RuntimeException(
                    "La fecha de devolución debe ser posterior a la fecha actual"
            );
        }

        // 3️. Validación de sanción activa
        boolean tieneSancionActiva = sancionRepository
                .existsSancionActiva(usuario.getId(), hoy);

        if (tieneSancionActiva) {
            throw new RuntimeException(
                    "El usuario tiene una sanción activa y no puede realizar préstamos"
            );
        }

        // 4️. Validación de límite de préstamos según rol
        long prestamosActivos = prestamoRepository
                .contarPrestamosActivos(usuario.getId());

        String rol = usuario.getRol().getNombre();

        if ("ALUMNO".equalsIgnoreCase(rol) && prestamosActivos >= 3) {
            throw new RuntimeException(
                    "El alumno ya alcanzó el límite máximo de 3 préstamos activos"
            );
        }

        if ("DOCENTE".equalsIgnoreCase(rol) && prestamosActivos >= 5) {
            throw new RuntimeException(
                    "El docente ya alcanzó el límite máximo de 5 préstamos activos"
            );
        }

        // 5️. Validación de disponibilidad del ejemplar
        // (doble seguro ante accesos concurrentes)
        if (ejemplar.getEstado() != EstadoEjemplar.disponible) {
            throw new RuntimeException(
                    "El ejemplar no se encuentra disponible para préstamo"
            );
        }

        // 6️. Creación del préstamo
        // Se asocia el ejemplar directamente para facilitar vistas y reportes
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setOperador(operador);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setEstado(EstadoPrestamo.pendiente);

        // 7️. Actualización del estado del ejemplar
        ejemplar.setEstado(EstadoEjemplar.prestado);
        ejemplarRepository.save(ejemplar);

        // 8️. Persistencia del préstamo
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        // 9️. Registro del detalle del préstamo
        // Usado para reportes e históricos administrativos
        DetallePrestamo detalle = new DetallePrestamo();
        detalle.setPrestamo(prestamoGuardado);
        detalle.setEjemplar(ejemplar);
        detallePrestamoRepository.save(detalle);

        return prestamoGuardado;
    }

    // Historial general de préstamos (Admin / Bibliotecario)
    @Override
    public List<Prestamo> listarHistorial() {
        return prestamoRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Prestamo::getFechaPrestamo).reversed())
                .toList();
    }

    // Obtención puntual de un préstamo (detalle administrativo)
    @Override
    public Prestamo obtenerPorId(Integer id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Préstamo no encontrado"));
    }

    // Métricas para Dashboard (Admin / Bibliotecario)
    @Override
    public long contarPrestamosRegistrados() {
        return prestamoRepository.count();
    }

    @Override
    public long contarDevolucionesPendientes() {
        return prestamoRepository.countByEstado(EstadoPrestamo.pendiente);
    }

    // Métrica personal: préstamos activos (Alumno / Docente)
    @Override
    public long contarPrestamosActivosPorUsuario(Integer usuarioId) {
        return prestamoRepository.countPrestamosActivosPorUsuario(usuarioId);
    }
}
