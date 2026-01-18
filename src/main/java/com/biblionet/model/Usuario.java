package com.biblionet.model;

import jakarta.persistence.*;

/*
 * Entidad Usuario
 * -------------------------------------------------
 * Representa la identidad mínima para autenticación y autorización.
 * Esta entidad NO se usa para lógica de negocio ni reportes.
 * Su único propósito es login, estado de acceso y rol.
 */
@Entity
@Table(name = "users")
public class Usuario {

    // Identificador interno del usuario (login)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Usuario usado para iniciar sesión (único en el sistema)
    @Column(name = "usuario_login", nullable = false, unique = true, length = 50)
    private String usuarioLogin;

    // Clave de acceso (no contiene datos personales)
    @Column(nullable = false, length = 100)
    private String clave;

    // Estado del acceso al sistema (controla si puede iniciar sesión)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('activo','inactivo') DEFAULT 'activo'")
    private Estado estado = Estado.activo;

    // Rol que define permisos y vistas disponibles
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Rol rol;

    // Estados posibles del acceso
    public enum Estado {
        activo, inactivo
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsuarioLogin() { return usuarioLogin; }
    public void setUsuarioLogin(String usuarioLogin) { this.usuarioLogin = usuarioLogin; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
