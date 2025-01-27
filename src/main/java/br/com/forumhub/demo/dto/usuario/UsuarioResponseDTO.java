package br.com.forumhub.demo.dto.usuario;

import br.com.forumhub.demo.dto.topico.TopicoResponseDTO;
import br.com.forumhub.demo.model.entities.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String role,
        List<TopicoResponseDTO> topicosFeito
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().toString(),
                usuario.getTopicos().stream().map(TopicoResponseDTO::new).collect(Collectors.toList())
        );
    }
}
