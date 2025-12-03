package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    // Ver reportes abiertos/pendientes
    // Asumiendo que definimos el estado como String o Enum
    List<Reporte> findByEstado(String estado);

    // Ver si un usuario ha sido reportado muchas veces
    List<Reporte> findByReportadoId(Long reportadoId);
}