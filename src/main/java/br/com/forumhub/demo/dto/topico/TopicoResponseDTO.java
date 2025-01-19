package br.com.forumhub.demo.dto.topico;

import br.com.forumhub.demo.model.entities.Topico;
import br.com.forumhub.demo.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDate dataCriacao,
        Status status,
        Long autorId
) {
    public TopicoResponseDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getId()
        );
    }
}
