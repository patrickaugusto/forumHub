package br.com.forumhub.demo.model;

import br.com.forumhub.demo.dtos.DadosAtualizarTopico;
import br.com.forumhub.demo.dtos.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDate dataCriacao;
    private String status;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    public Topico(DadosCadastroTopico topicoDto, Usuario autor, Curso curso) {
        this.titulo = topicoDto.titulo();
        this.mensagem = topicoDto.mensagem();
        this.dataCriacao = LocalDate.now();
        this.status = "ABERTO";
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(DadosAtualizarTopico topicoDto) {
        this.titulo = topicoDto.titulo();
        this.mensagem = topicoDto.mensagem();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}