package br.com.forumhub.demo.dtos;

import br.com.forumhub.demo.model.Topico;

import java.time.LocalDate;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDate dataCriacao,
        String status,
        String autor,
        String curso
) {
    public DadosListagemTopico(Topico topico) {
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
