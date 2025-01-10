package br.com.forumhub.demo.dtos.topico;

import br.com.forumhub.demo.model.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoCreateDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long autorId,
        @NotNull
        Long cursoId
) {
        public TopicoCreateDTO(Topico topico) {
                this(
                        topico.getTitulo(),
                        topico.getMensagem(),
                        topico.getAutor().getId(),
                        topico.getCurso().getId()
                );
        }
}
