package br.com.forumhub.demo.dto.resposta;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RespostaUpdateDTO(
        @NotBlank
        String mensagem
        ) {
}
