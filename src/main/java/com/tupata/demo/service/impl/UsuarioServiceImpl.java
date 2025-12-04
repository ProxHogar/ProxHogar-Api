package com.tupata.demo.service.impl;

import com.tupata.demo.dto.ConvertirATrabajadorDTO;
import com.tupata.demo.dto.RegistroUsuarioDTO;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.repository.TrabajadorRepository;
import com.tupata.demo.repository.UsuarioRepository;
import com.tupata.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TrabajadorRepository trabajadorRepository;

    // Inyectamos el codificador de contraseñas de Spring Security
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioDTO dto) {
        // 1. Validar si el email ya existe
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // 2. Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());

        // --- CAMBIO DE SEGURIDAD ---
        // Aquí encriptamos la contraseña ("123456" -> "$2a$10$EixZa...")
        // Nunca guardamos la contraseña en texto plano
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        // ---------------------------

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFcmToken(dto.getFcmToken());

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Trabajador convertirEnTrabajador(Long usuarioId, ConvertirATrabajadorDTO dto) {
        Usuario usuario = buscarPorId(usuarioId);

        if (trabajadorRepository.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("Este usuario ya es un trabajador");
        }

        Trabajador trabajador = new Trabajador();
        trabajador.setUsuario(usuario);
        trabajador.setBiografia(dto.getBiografia());
        trabajador.setDni(dto.getDni());
        trabajador.setAntecedentesPenalesVerificados(dto.isAntecedentesPenalesVerificados());
        trabajador.setFotoBiometricaReferenciaUrl(dto.getFotoBiometricaReferenciaUrl());

        trabajador.setCalificacionPromedio(5.0);
        trabajador.setTrabajosCompletados(0);
        trabajador.setDisponible(true);
        trabajador.setVerificado(false);

        return trabajadorRepository.save(trabajador);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}