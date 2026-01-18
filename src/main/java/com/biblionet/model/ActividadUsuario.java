package com.biblionet.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actividad_usuario")
public class ActividadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Usuario que realiza la consulta
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioCrud usuario;

    // Tipo de actividad (solo CONSULTA)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_actividad", nullable = false)
    private TipoActividad tipoActividad;

    // Descripción simple de la acción
    @Column(nullable = false, length = 255)
    private String descripcion;

    // Fecha y hora de la actividad
    @Column(nullable = false)
    private LocalDateTime fecha;

    // Constructor vacío (JPA)
    public ActividadUsuario() {
    }

    // Constructor directo para registrar la actividad
    public ActividadUsuario(
            UsuarioCrud usuario,
            TipoActividad tipoActividad,
            String descripcion
    ) {
        this.usuario = usuario;
        this.tipoActividad = tipoActividad;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public UsuarioCrud getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioCrud usuario) {
        this.usuario = usuario;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
