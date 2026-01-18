package com.biblionet.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/*
 * Entidad Prestamo
 * -------------------------------------------------
 * Representa el núcleo operativo del sistema.
 * Un préstamo siempre se realiza sobre un Ejemplar
 * y vincula a un usuario final y a un operador del sistema.
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {

    // Identificador único del préstamo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Usuario al que se le realiza el préstamo.
     * Puede ser Alumno o Docente.
     * Se usa UsuarioCrud porque forma parte de la lógica de negocio.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioCrud usuario;

    /*
     * Operador que registra el préstamo en el sistema.
     * Normalmente Administrador o Bibliotecario.
     * Permite trazabilidad de quién ejecutó la acción.
     */
    @ManyToOne
    @JoinColumn(name = "operador_id", nullable = false)
    private UsuarioCrud operador;
    
    /*
     * Ejemplar específico que se presta.
     * El préstamo NO se hace sobre el libro, sino sobre el ejemplar.
     */
    @ManyToOne
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;
    
    // Fecha en la que se registra el préstamo
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    // Fecha real de devolución (null mientras esté pendiente)
    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    /*
     * Estado actual del préstamo.
     * Controla el flujo: pendiente, devuelto o atrasado.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado = EstadoPrestamo.pendiente;

    // ===== Getters y Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioCrud getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioCrud usuario) {
        this.usuario = usuario;
    }

    public UsuarioCrud getOperador() {
        return operador;
    }

    public void setOperador(UsuarioCrud operador) {
        this.operador = operador;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }
}
