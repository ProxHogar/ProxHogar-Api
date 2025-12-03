package com.tupata.demo.controller;

import com.tupata.demo.dto.LoginDTO;
import com.tupata.demo.dto.RegistroUsuarioDTO; // <--- No olvides importar esto
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.security.JwtUtil;
import com.tupata.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    // 1. REGISTRO (Agregado)
    // URL: POST /api/auth/registro
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroUsuarioDTO dto) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // 2. LOGIN
    // URL: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginRequest) {
        try {
            // Validar credenciales
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas o usuario no existe");
        }

        // Generar Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Devolver info extra
        var usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("usuarioId", usuario.getId());
        response.put("email", usuario.getEmail());
        response.put("nombreCompleto", usuario.getNombreCompleto());
        // Verificamos si tiene perfil de trabajador
        response.put("esTrabajador", usuario.getTrabajador() != null);

        return ResponseEntity.ok(response);
    }
}