package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    // Buscar todas las reseñas RECIBIDAS por un usuario (para su perfil)
    List<Resena> findByReceptorId(Long receptorId);

    // Buscar reseñas escritas por un usuario (historial)
    List<Resena> findByAutorId(Long autorId);
}