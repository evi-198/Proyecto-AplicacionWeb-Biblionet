package com.biblionet.dto;

import com.biblionet.model.EstadoSancion;
import java.time.LocalDate;

// DTO usado para el reporte de Usuarios Sancionados (rol Administrador)
// Centraliza datos del usuario + datos de la sanción para evitar exponer entidades completas
public class UsuarioSancionadoDTO {

    // ---- Datos básicos del usuario ----
    private Integer usuarioId;
    private String nombre;
    private String apellido;
    private String dni;
    private String usuarioLogin;
    private String rol;

    // ---- Información de la sanción ----
    private String motivo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSancion estado;

    // Constructor usado directamente desde consultas JPQL
    // Permite mapear resultados sin usar entidades completas
    public UsuarioSancionadoDTO(
            Integer usuarioId,
            String nombre,
            String apellido,
            String dni,
            String usuarioLogin,
            String rol,
            String motivo,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            EstadoSancion estado
    ) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuarioLogin = usuarioLogin;
        this.rol = rol;
        this.motivo = motivo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // ---- Getters (solo lectura, DTO inmutable) ----

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public String getRol() {
        return rol;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoSancion getEstado() {
        return estado;
    }
}
