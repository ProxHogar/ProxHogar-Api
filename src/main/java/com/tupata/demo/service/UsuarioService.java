package com.tupata.demo.service;

import com.tupata.demo.dto.ConvertirATrabajadorDTO;
import com.tupata.demo.dto.RegistroUsuarioDTO;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.entity.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(RegistroUsuarioDTO dto);
    Trabajador convertirEnTrabajador(Long usuarioId, ConvertirATrabajadorDTO dto);
    Usuario buscarPorEmail(String email);
    Usuario buscarPorId(Long id);
}