package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Buscar por nombre (ej: para no crear duplicados)
    Optional<Categoria> findByNombre(String nombre);
}