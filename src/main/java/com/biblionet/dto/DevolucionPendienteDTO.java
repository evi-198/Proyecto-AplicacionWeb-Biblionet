package com.biblionet.dto;

import java.time.LocalDate;
import com.biblionet.model.EstadoPrestamo;

// DTO usado en el reporte de Devoluciones Pendientes (rol Bibliotecario)
// Agrupa información del préstamo, usuario y ejemplar en una sola estructura de lectura
public class DevolucionPendienteDTO {

    // ---- Información del libro y ejemplar ----
    private String tituloLibro;
    private Integer codigoEjemplar;
    private String ubicacionEjemplar;

    // ---- Información del usuario y préstamo ----
    private String nombreUsuario;
    private LocalDate fechaPrestamo;
    private EstadoPrestamo estado;

    // Constructor utilizado directamente en consultas JPQL
    // Evita exponer entidades completas en reportes
    public DevolucionPendienteDTO(
            String tituloLibro,
            Integer codigoEjemplar,
            String nombreUsuario,
            LocalDate fechaPrestamo,
            String ubicacionEjemplar,
            EstadoPrestamo estado
    ) {
        this.tituloLibro = tituloLibro;
        this.codigoEjemplar = codigoEjemplar;
        this.nombreUsuario = nombreUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.ubicacionEjemplar = ubicacionEjemplar;
        this.estado = estado;
    }

    // ---- Getters (DTO de solo lectura) ----
    public String getTituloLibro() {
        return tituloLibro;
    }

    public Integer getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getUbicacionEjemplar() {
        return ubicacionEjemplar;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }
}
