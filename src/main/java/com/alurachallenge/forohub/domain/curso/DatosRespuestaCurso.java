package com.alurachallenge.forohub.domain.curso;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        CategoriaCurso categoria
) {
    public DatosRespuestaCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria());
    }
}
