package com.biblionet.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_prestamo")
public class DetallePrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Muchos detalles pueden pertenecer a un préstamo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    // El ejemplar usado en el préstamo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    // ======================
    // Constructores
    // ======================

    public DetallePrestamo() {
    }

    public DetallePrestamo(Prestamo prestamo, Ejemplar ejemplar) {
        this.prestamo = prestamo;
        this.ejemplar = ejemplar;
    }

    // ======================
    // Getters y Setters
    // ======================

    public Integer getId() {
        return id;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
