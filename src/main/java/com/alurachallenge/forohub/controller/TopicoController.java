package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.curso.CursoRepository;
import com.alurachallenge.forohub.domain.topico.*;
import com.alurachallenge.forohub.domain.usuario.Usuario;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                                        UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje()).isPresent()) {
            return ResponseEntity.status(409).body(null);
        }

        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));
        var curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Topico topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);

        topicoRepository.save(topico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<DatosListadoTopico> topicos = topicoRepository.findAll(pageable)
                .map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datos) {
        Topico topico = topicoRepository.findById(datos.id())
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
            if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {

                return ResponseEntity.status(403).body("No tienes permisos para actualizar este tópico.");
            }
        } else {

            return ResponseEntity.status(403).body("No tienes permisos para actualizar este tópico.");
        }

        topico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
            if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {

                return ResponseEntity.status(403).body("No tienes permisos para eliminar este tópico.");
            }
        } else {

            return ResponseEntity.status(403).body("No tienes permisos para eliminar este tópico.");
        }

        topicoRepository.delete(topico);

        return ResponseEntity.noContent().build();
    }
}
