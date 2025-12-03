package com.tupata.demo.service.impl;

import com.tupata.demo.dto.SuscribirseDTO;
import com.tupata.demo.model.entity.Plan;
import com.tupata.demo.model.entity.Suscripcion;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.repository.PlanRepository;
import com.tupata.demo.repository.SuscripcionRepository;
import com.tupata.demo.repository.TrabajadorRepository; // <--- Nuevo
import com.tupata.demo.repository.UsuarioRepository;
import com.tupata.demo.service.SuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuscripcionServiceImpl implements SuscripcionService {

    private final PlanRepository planRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabajadorRepository trabajadorRepository; // <--- Inyectamos esto

    @Override
    public List<Plan> listarPlanes(String rol) {
        if (rol != null && !rol.isEmpty()) {
            return planRepository.findByRolObjetivo(rol);
        }
        return planRepository.findAll();
    }


    @Override
    @Transactional
    public Suscripcion suscribirse(Long usuarioId, SuscribirseDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        // 1. Desactivar suscripciones anteriores
        Optional<Suscripcion> actual = suscripcionRepository.findByUsuarioIdAndActivaTrue(usuarioId);
        if (actual.isPresent()) {
            Suscripcion old = actual.get();
            old.setActiva(false);
            suscripcionRepository.save(old);
        }

        // 2. Crear nueva suscripción
        Suscripcion nueva = new Suscripcion();
        nueva.setUsuario(usuario);
        nueva.setPlan(plan);
        nueva.setFechaInicio(LocalDateTime.now());

        int meses = (dto.getMeses() != null && dto.getMeses() > 0) ? dto.getMeses() : 1;
        nueva.setFechaFin(LocalDateTime.now().plusMonths(meses));

        nueva.setActiva(true);

        // --- LÓGICA DE BENEFICIOS (NUEVO) ---
        // Si el plan cuesta más de 0 (es pagado), damos beneficios VIP
        if (plan.getPrecio() > 0) {
            aplicarBeneficiosPremium(usuario);
        }

        return suscripcionRepository.save(nueva);
    }

    private void aplicarBeneficiosPremium(Usuario usuario) {
        // Buscar si el usuario tiene perfil de trabajador
        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByUsuarioId(usuario.getId());

        if (trabajadorOpt.isPresent()) {
            Trabajador t = trabajadorOpt.get();
            // BENEFICIO 1: Perfil Verificado (Check Azul)
            t.setVerificado(true);
            // BENEFICIO 2: Aumentar visibilidad (Podríamos usar un campo 'destacado' o usar 'verificado' para ordenar)
            trabajadorRepository.save(t);
        }
    }

    @Override
    public Suscripcion obtenerSuscripcionActual(Long usuarioId) {
        return suscripcionRepository.findByUsuarioIdAndActivaTrue(usuarioId)
                .orElse(null);
    }

}