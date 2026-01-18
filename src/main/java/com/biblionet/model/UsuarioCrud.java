package com.biblionet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

// ENTIDAD PRINCIPAL DE USUARIOS DEL SITEMAS (BIBLIONET)
// Se usa para toda la lógica de negocio: roles, préstamos, sanciones, reportes

public class UsuarioCrud {

    // Identificador interno del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Datos personales básicos
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    // Documento único de identificación
    @Column(nullable = false, length = 15, unique = true)
    private String dni;

    // Información de contacto (opcional)
    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 120)
    private String correo;

    // Credencial de acceso (login del sistema)
    @Column(name = "usuario_login", nullable = false, unique = true, length = 50)
    private String usuarioLogin;

    // Clave asociada al usuario (gestionada por la capa de servicio)
    @Column(nullable = false, length = 100)
    private String clave;

    // Estado lógico del usuario dentro del sistema
    // Se usa para habilitar o bloquear acceso sin eliminar registros
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('activo','inactivo') DEFAULT 'activo'")
    private Estado estado = Estado.activo;

    // Origen del usuario dentro del sistema
    // Permite diferenciar usuarios creados por administración o por registro
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('empleado','usuario') DEFAULT 'usuario'", nullable = false)
    private Origen origen = Origen.usuario;

    // Rol funcional del usuario (Administrador, Bibliotecario, Docente, Alumno)
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Rol rol;

    // --------------------
    // Constructores
    // --------------------

    // Constructor vacío requerido por JPA
    public UsuarioCrud() {
    }

    // Constructor usado para creación controlada de usuarios
    // Define valores por defecto coherentes con el sistema
    public UsuarioCrud(String nombre, String apellido, String dni, String usuarioLogin, 
                   String clave, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuarioLogin = usuarioLogin;
        this.clave = clave;
        this.rol = rol;
        this.estado = Estado.activo;
        this.origen = Origen.usuario;
    }

    // --------------------
    // Getters y setters
    // --------------------

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getUsuarioLogin() { return usuarioLogin; }
    public void setUsuarioLogin(String usuarioLogin) { this.usuarioLogin = usuarioLogin; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Origen getOrigen() { return origen; }
    public void setOrigen(Origen origen) { this.origen = origen; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    // --------------------
    // Enums de dominio
    // --------------------

    // Estado lógico del usuario
    public enum Estado {
        activo, inactivo
    }

    // Origen de creación del usuario
    public enum Origen {
        empleado, usuario
    }
}
