package com.biblionet.service.impl;

import com.biblionet.model.Ejemplar;
import com.biblionet.model.EstadoEjemplar;
import com.biblionet.repository.EjemplarRepository;
import com.biblionet.service.EjemplarService;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Servicio de Ejemplares
 * -------------------------------------------------
 * Centraliza la gestión operativa de ejemplares:
 * listados, disponibilidad y soporte directo
 * al flujo de préstamos.
 *
 * Es una pieza clave entre Libros y Préstamos.
 */
@Service
public class EjemplarServiceImpl implements EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    public EjemplarServiceImpl(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    // Listado general de ejemplares (uso administrativo)
    @Override
    public List<Ejemplar> listarTodos() {
        return ejemplarRepository.findAll();
    }

    // Listado de ejemplares filtrados por estado
    @Override
    public List<Ejemplar> listarPorEstado(EstadoEjemplar estado) {
        return ejemplarRepository.findByEstado(estado);
    }

    // Flujo crítico de préstamos:
    // obtiene solo los ejemplares disponibles de un libro
    @Override
    public List<Ejemplar> listarDisponiblesPorLibro(Integer libroId) {
        return ejemplarRepository.findByLibro_IdAndEstado(
            libroId,
            EstadoEjemplar.disponible
        );
    }

    // Obtiene un ejemplar específico (detalle / validaciones)
    @Override
    public Ejemplar buscarPorId(Integer id) {
        return ejemplarRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
    }

    // Registro o actualización de ejemplares
    @Override
    public Ejemplar guardar(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }
}
