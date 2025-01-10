package br.com.forumhub.demo.dtos.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoCreateDTO(
        @NotBlank(message = "O nome do curso é obrigatório.")
        String nome,

        @NotBlank(message = "A categoria do curso é obrigatória.")
        String categoria
) {
}
