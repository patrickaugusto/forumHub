package br.com.forumhub.demo.model;

import br.com.forumhub.demo.dtos.usuario.UsuarioCreateDTO;
import br.com.forumhub.demo.dtos.usuario.UsuarioUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    //@ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(
    //        name = "usuario_perfil",
    //        joinColumns = @JoinColumn(name = "usuario_id"),
    //        inverseJoinColumns = @JoinColumn(name = "perfil_id")
    //)
    //private Set<Perfil> perfis;

    public Usuario(UsuarioCreateDTO dadosUsuario) {
        this.nome = dadosUsuario.nome();
        this.email = dadosUsuario.email();
        this.senha = dadosUsuario.senha();
    }

    public Usuario(@Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        this.nome = usuarioUpdateDTO.nome();
        this.email = usuarioUpdateDTO.email();
        this.senha = usuarioUpdateDTO.senha();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    //public Set<Perfil> getPerfis() {
    //    return perfis;
    //}
}