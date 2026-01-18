package com.biblionet.repository;

import com.biblionet.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repositorio de Categoría
 * -------------------------------------------------
 * Maneja las operaciones básicas (CRUD) de las categorías de libros.
 * Se apoya completamente en JpaRepository ya que, por ahora,
 * no requiere consultas personalizadas.
 *
 * Si en el futuro se agregan reportes o filtros específicos
 * (ej: categorías más usadas), este será el punto de extensión.
 */

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
