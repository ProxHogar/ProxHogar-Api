package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Para el login
    Optional<Usuario> findByEmail(String email);

    // Para verificar si un correo ya existe al registrarse
    boolean existsByEmail(String email);
}