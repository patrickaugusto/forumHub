package br.com.forumhub.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem
) {
}
