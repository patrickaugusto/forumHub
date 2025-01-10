package br.com.forumhub.demo.model;

import br.com.forumhub.demo.dtos.resposta.RespostaCreateDTO;
import br.com.forumhub.demo.dtos.resposta.RespostaUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;
    private LocalDate dataCriacao;
    private Boolean solucao;

    @ManyToOne
    private Topico topico;

    @ManyToOne
    private Usuario autor;

    public Resposta(Resposta resposta, Topico topico, Usuario autor) {
        this.mensagem = resposta.mensagem;
        this.dataCriacao = LocalDate.now();
        this.solucao = resposta.solucao;
        this.autor = autor;
        this.topico = topico;
    }

    public Resposta(Resposta respostaAtualizada) {

    }

    public Resposta(@Valid RespostaCreateDTO respostaCreateDTO, Topico topico, Usuario autor) {
        this.mensagem = respostaCreateDTO.mensagem();
        this.dataCriacao = LocalDate.now();
        this.solucao = false;
        this.autor = autor;
        this.topico = topico;
    }

    public Resposta(@Valid RespostaUpdateDTO respostaUpdateDTO) {
        this.mensagem = respostaUpdateDTO.mensagem();
        this.solucao = respostaUpdateDTO.solucao();
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Boolean getSolucao() {
        return solucao;
    }

    public Topico getTopico() {
        return topico;
    }

    public Usuario getAutor() {
        return autor;
    }
}