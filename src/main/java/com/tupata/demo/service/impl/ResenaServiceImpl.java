package com.tupata.demo.service.impl;

import com.tupata.demo.dto.CrearResenaDTO;
import com.tupata.demo.model.entity.Resena;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.repository.ResenaRepository;
import com.tupata.demo.repository.SolicitudRepository;
import com.tupata.demo.repository.TrabajadorRepository;
import com.tupata.demo.repository.UsuarioRepository;
import com.tupata.demo.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Override
    @Transactional
    public Resena crearResena(CrearResenaDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario autor = usuarioRepository.findById(dto.getUsuarioCalificadoId()) // Ojo: aqui deberia ser el ID del token (quien califica)
                .orElseThrow(() -> new RuntimeException("Usuario autor no encontrado")); // SIMPLIFICADO

        Usuario receptor = usuarioRepository.findById(dto.getUsuarioCalificadoId())
                .orElseThrow(() -> new RuntimeException("Usuario a calificar no encontrado"));

        Resena resena = new Resena();
        resena.setSolicitud(solicitud);
        resena.setReceptor(receptor);
        // En un caso real, el autor se saca del SecurityContext, aquí lo asumimos o pasamos
        // Para que compile sin Login, asumiremos que el cliente de la solicitud es el autor
        resena.setAutor(solicitud.getCliente());

        resena.setCalificacion(dto.getEstrellas());
        resena.setComentario(dto.getComentario());

        Resena resenaGuardada = resenaRepository.save(resena);

        // *** ACTUALIZAR PROMEDIO DEL TRABAJADOR SI EL RECEPTOR ES TRABAJADOR ***
        if (receptor.getTrabajador() != null) {
            actualizarPromedioTrabajador(receptor.getTrabajador());
        }

        return resenaGuardada;
    }

    private void actualizarPromedioTrabajador(Trabajador trabajador) {
        List<Resena> resenas = resenaRepository.findByReceptorId(trabajador.getUsuario().getId());
        if (resenas.isEmpty()) return;

        double promedio = resenas.stream()
                .mapToInt(Resena::getCalificacion)
                .average()
                .orElse(0.0);

        trabajador.setCalificacionPromedio(promedio);
        trabajador.setTrabajosCompletados(trabajador.getTrabajosCompletados() + 1);
        trabajadorRepository.save(trabajador);
    }

    @Override
    public List<Resena> listarResenasDeUsuario(Long usuarioId) {
        return resenaRepository.findByReceptorId(usuarioId);
    }
}