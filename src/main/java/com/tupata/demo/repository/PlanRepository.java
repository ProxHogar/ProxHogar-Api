package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    // Método simple para listar
    List<Plan> findByRolObjetivo(String rolObjetivo);
}