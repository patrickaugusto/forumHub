package br.com.forumhub.demo.dtos;

import br.com.forumhub.demo.model.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long autorId,
        @NotNull
        Long cursoId
) {
        public DadosCadastroTopico(Topico topico) {
                this(
                        topico.getTitulo(),
                        topico.getMensagem(),
                        topico.getAutor().getId(),
                        topico.getCurso().getId()
                );
        }
}
