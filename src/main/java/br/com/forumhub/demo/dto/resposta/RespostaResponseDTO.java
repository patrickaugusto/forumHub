package br.com.forumhub.demo.dto.resposta;

import br.com.forumhub.demo.model.entities.Resposta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RespostaResponseDTO(
        Long id,
        String mensagem,
        Long topicoId,
        Long usuarioId,
        String nomeUsuario,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDateTime dataHora,
        Integer curtida
) {
    public RespostaResponseDTO(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getTopico().getId(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getNome(),
                resposta.getDataHora(),
                resposta.getCurtida()
        );
    }
}
