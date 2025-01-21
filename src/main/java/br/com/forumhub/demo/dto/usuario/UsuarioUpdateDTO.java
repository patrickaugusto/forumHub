package br.com.forumhub.demo.dto.usuario;

import br.com.forumhub.demo.model.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email
) {
    public UsuarioUpdateDTO(Usuario usuario){
        this(
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
