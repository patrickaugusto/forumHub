package br.com.forumhub.demo.dtos.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaCreateDTO(

    @NotBlank(message = "A mensagem é obrigatória.")
    String mensagem,

    @NotNull(message = "O ID do autor é obrigatório.")
    Long autorId,

    @NotNull(message = "O ID do tópico é obrigatório.")
    Long topicoId
) {
}
