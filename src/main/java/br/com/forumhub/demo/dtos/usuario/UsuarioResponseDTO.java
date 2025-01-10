package br.com.forumhub.demo.dtos.usuario;

import br.com.forumhub.demo.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
        //Set<PerfilDTO> perfis
) {

    public UsuarioResponseDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
