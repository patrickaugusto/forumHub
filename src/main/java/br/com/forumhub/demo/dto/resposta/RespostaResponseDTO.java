package br.com.forumhub.demo.dto.resposta;

import br.com.forumhub.demo.model.entities.Resposta;

import java.time.LocalDateTime;

public record RespostaResponseDTO(
        Long id,
        String mensagem,
        Long topicoId,
        String nomeUsuario,
        LocalDateTime dataHora,
        Integer curtida
) {
    public RespostaResponseDTO(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getTopico().getId(),
                resposta.getUsuario().getNome(),
                resposta.getDataHora(),
                resposta.getCurtida()
        );
    }
}
