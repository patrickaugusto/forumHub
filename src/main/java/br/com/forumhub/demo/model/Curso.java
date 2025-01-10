package br.com.forumhub.demo.model;

import br.com.forumhub.demo.dtos.curso.CursoCreateDTO;
import br.com.forumhub.demo.dtos.curso.CursoUpdateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String categoria;

    public Curso() {}

    public Curso(CursoCreateDTO cursoCreateDTO) {
        this.nome = cursoCreateDTO.nome();
        this.categoria = cursoCreateDTO.categoria();
    }

    public Curso(CursoUpdateDTO cursoAtualizado) {
        this.nome = cursoAtualizado.nome();
        this.categoria = cursoAtualizado.categoria();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }
}