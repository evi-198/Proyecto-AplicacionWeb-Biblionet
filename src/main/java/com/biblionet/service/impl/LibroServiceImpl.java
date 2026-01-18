package com.biblionet.service.impl;

import com.biblionet.model.Libro;
import com.biblionet.repository.LibroRepository;
import com.biblionet.service.LibroService;
import org.springframework.stereotype.Service;
import com.biblionet.repository.EjemplarRepository;

import java.util.List;
import java.util.Optional;

/*
 * Servicio de Libros
 * -------------------------------------------------
 * Centraliza la gestión principal de libros:
 * registro, mantenimiento y métricas generales.
 *
 * Soporta dashboards administrativos y
 * consultas de disponibilidad para usuarios.
 */
@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final EjemplarRepository ejemplarRepository;

    public LibroServiceImpl(LibroRepository libroRepository, EjemplarRepository ejemplarRepository) {
        this.libroRepository = libroRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    // Listado general de libros (uso administrativo)
    @Override
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    // Búsqueda puntual de libro por ID
    @Override
    public Optional<Libro> buscarPorId(Integer id) {
        return libroRepository.findById(id);
    }

    // Registro o actualización de libros
    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    // Eliminación directa de libros
    @Override
    public void eliminar(Integer id) {
        libroRepository.deleteById(id);
    }

    // Métrica global: total de libros registrados (Dashboard Admin / Bibliotecario)
    @Override
    public long contarLibros() {
        return libroRepository.count();
    }

    // Métrica operativa:
    // libros que tienen al menos un ejemplar disponible (Alumno / Docente)
    @Override
    public long contarLibrosConEjemplaresDisponibles() {
        return ejemplarRepository.contarLibrosConEjemplarDisponible();
    }
}
