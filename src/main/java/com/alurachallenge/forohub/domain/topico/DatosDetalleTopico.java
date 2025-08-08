package com.alurachallenge.forohub.domain.topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        StatusTopico status,
        Long autorId,
        Long cursoId,
        List<DatosRespuesta> respuestas
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getCurso().getId(),
                topico.getRespuestas()
                        .stream()
                        .map(DatosRespuesta::new) // Crea un nuevo DatosRespuesta por cada Respuesta
                        .collect(Collectors.toList())
        );
    }
}
