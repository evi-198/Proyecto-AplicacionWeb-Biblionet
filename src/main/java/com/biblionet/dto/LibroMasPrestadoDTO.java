package com.biblionet.dto;

// DTO usado en el reporte "Libros Más Prestados" (rol Administrador)
// Representa un resumen agregado por libro, no un préstamo individual
public class LibroMasPrestadoDTO {

    // ---- Identificación del libro ----
    private Integer libroId;
    private String titulo;
    private String nombreCategoria;

    // ---- Métrica principal del reporte ----
    private Long totalPrestamos;

    // Constructor vacío requerido 
    public LibroMasPrestadoDTO() {
    }

    // Constructor utilizado exactamente por la consulta JPQL del reporte
    // Mapea resultados agregados sin exponer entidades completas
    public LibroMasPrestadoDTO(
            Integer libroId,
            String titulo,
            String nombreCategoria,
            Long totalPrestamos
    ) {
        this.libroId = libroId;
        this.titulo = titulo;
        this.nombreCategoria = nombreCategoria;
        this.totalPrestamos = totalPrestamos;
    }

    // ---- Getters (DTO de solo lectura para reportes) ----
    public Integer getLibroId() {
        return libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public Long getTotalPrestamos() {
        return totalPrestamos;
    }
}
