package com.biblionet.dto;

import java.time.LocalDate;
import com.biblionet.model.EstadoPrestamo;

// DTO usado para el historial de préstamos (rol Alumno y Docente)
// Representa una vista simplificada del préstamo para consultas personales
public class HistorialPrestamoDTO {

    // ---- Información del libro y ejemplar ----
    private String tituloLibro;
    private Integer codigoEjemplar;

    // ---- Fechas clave del préstamo ----
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    // ---- Estado actual del préstamo ----
    private EstadoPrestamo estado;

    // Constructor utilizado en consultas JPQL
    // Permite mapear el historial sin exponer entidades completas
    public HistorialPrestamoDTO(
            String tituloLibro,
            Integer codigoEjemplar,
            LocalDate fechaPrestamo,
            LocalDate fechaDevolucion,
            EstadoPrestamo estado
    ) {
        this.tituloLibro = tituloLibro;
        this.codigoEjemplar = codigoEjemplar;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    // ---- Getters (DTO de solo lectura) ----
    public String getTituloLibro() {
        return tituloLibro;
    }

    public Integer getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }
}
