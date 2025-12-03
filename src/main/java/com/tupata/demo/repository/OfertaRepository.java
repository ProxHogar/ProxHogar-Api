package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    // --- CAMBIO IMPORTANTE ---
    // Antes: findBySolicitudId
    // Ahora: Ordenamos por 'trabajador.verificado' DESCENDENTE (true va primero que false)
    // Spring Data JPA traduce esto a SQL automáticamente.

    List<Oferta> findBySolicitudIdOrderByTrabajadorVerificadoDesc(Long solicitudId);

    // Para ver mis postulaciones
    List<Oferta> findByTrabajadorId(Long trabajadorId);
}