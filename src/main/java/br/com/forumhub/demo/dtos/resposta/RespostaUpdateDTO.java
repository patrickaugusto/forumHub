package br.com.forumhub.demo.dtos.resposta;

import jakarta.validation.constraints.NotBlank;

public record RespostaUpdateDTO(

    @NotBlank(message = "A mensagem é obrigatória.")
    String mensagem,

    Boolean solucao

) {
}
