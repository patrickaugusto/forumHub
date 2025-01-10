package br.com.forumhub.demo.dtos.topico;

import jakarta.validation.constraints.NotBlank;

public record TopicoUpdateDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem
) {
}
