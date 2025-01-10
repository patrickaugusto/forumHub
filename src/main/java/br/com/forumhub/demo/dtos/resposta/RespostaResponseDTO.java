package br.com.forumhub.demo.dtos.resposta;

import br.com.forumhub.demo.model.Resposta;

import java.time.LocalDate;

public record RespostaResponseDTO(

    Long id,
    String mensagem,
    LocalDate dataCriacao,
    Boolean solucao,
    Long autorId,
    Long topicoId

){
    public RespostaResponseDTO(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getSolucao(),
                resposta.getAutor().getId(),
                resposta.getTopico().getId()

        );
    }
}
