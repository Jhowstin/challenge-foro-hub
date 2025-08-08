package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.curso.Curso;
import com.alurachallenge.forohub.domain.curso.CursoRepository;
import com.alurachallenge.forohub.domain.curso.DatosRegistroCurso;
import com.alurachallenge.forohub.domain.curso.DatosRespuestaCurso;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datos,
                                                              UriComponentsBuilder uriComponentsBuilder) {

        // Creamos una instancia del curso
        Curso curso = new Curso(datos.nombre(), datos.categoria());

        // Lo guardamos en la base de datos
        cursoRepository.save(curso);

        // Generamos la URI para la respuesta 201 Created
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        // Devolvemos el DTO con los datos del curso guardado
        return ResponseEntity.created(url).body(new DatosRespuestaCurso(curso));
    }
}
