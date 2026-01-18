package com.biblionet.service.impl;

import com.biblionet.model.EstadoEjemplar;
import com.biblionet.model.Libro;
import com.biblionet.repository.LibroRepository;
import com.biblionet.service.ActividadUsuarioService;
import com.biblionet.service.ConsultaLibroService;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Servicio de consultas de libros para Alumnos y Docentes.
 * Se enfoca exclusivamente en la búsqueda de libros
 * que tengan ejemplares disponibles.
 */
@Service
public class ConsultaLibroServiceImpl implements ConsultaLibroService {

    private final LibroRepository libroRepository;
    private final ActividadUsuarioService actividadUsuarioService;

    public ConsultaLibroServiceImpl(LibroRepository libroRepository,
                                    ActividadUsuarioService actividadUsuarioService) {
        this.libroRepository = libroRepository;
        this.actividadUsuarioService = actividadUsuarioService;
    }

    // Búsqueda principal de libros disponibles (Alumno / Docente)
    @Override
    public List<Libro> buscarLibrosDisponibles(String texto, Integer categoriaId) {

        /*
         * Normalización del texto de búsqueda:
         * - Si viene vacío o solo con espacios, se envía como null
         * - Permite que la consulta JPQL ignore el filtro de texto
         */
        String filtroTexto = (texto != null && !texto.trim().isEmpty())
                ? texto.trim()
                : null;

        /*
         * La consulta solo considera libros que tengan
         * al menos un ejemplar en estado DISPONIBLE.
         * Esto garantiza coherencia con el flujo de préstamos.
         */
        return libroRepository.buscarLibrosDisponibles(
                EstadoEjemplar.disponible,
                filtroTexto,
                categoriaId
        );
    }

}
