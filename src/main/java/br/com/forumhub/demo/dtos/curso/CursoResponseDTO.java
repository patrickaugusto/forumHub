package br.com.forumhub.demo.dtos.curso;

import br.com.forumhub.demo.model.Curso;

public record CursoResponseDTO(
    Long id,
    String nome,
    String categoria

) {
    public CursoResponseDTO(Curso curso) {
        this(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria()
        );
    }
}
