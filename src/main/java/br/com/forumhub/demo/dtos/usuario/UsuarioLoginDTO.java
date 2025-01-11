package br.com.forumhub.demo.dtos.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO(

    @NotBlank(message = "O login é obrigatório.")
    String login,

    @NotBlank(message = "A senha é obrigatória.")
    String senha

) {
}
