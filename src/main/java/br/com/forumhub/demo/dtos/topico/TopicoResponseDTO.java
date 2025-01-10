package br.com.forumhub.demo.dtos.topico;

import br.com.forumhub.demo.model.Topico;

import java.time.LocalDate;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDate dataCriacao,
        String status,
        String autor,
        String curso
) {
    public TopicoResponseDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
    }
}
