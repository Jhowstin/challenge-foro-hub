package com.alurachallenge.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull // Usamos NotNull porque el ID no puede ser una cadena en blanco
        Long autorId,
        @NotNull
        Long cursoId
) {}
