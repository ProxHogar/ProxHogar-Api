package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    // Buscar perfil de trabajador por el ID de su usuario base
    Optional<Trabajador> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);

    // Listar trabajadores disponibles cerca (Básico, luego añadiremos geolocalización real)
    List<Trabajador> findByDisponibleTrue();
}