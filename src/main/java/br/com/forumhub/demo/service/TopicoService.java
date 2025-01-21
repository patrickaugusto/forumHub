package br.com.forumhub.demo.service;

import br.com.forumhub.demo.exceptions.UnauthorizedException;
import br.com.forumhub.demo.dto.topico.TopicoRegisterDTO;
import br.com.forumhub.demo.dto.topico.TopicoResponseDTO;
import br.com.forumhub.demo.dto.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.model.entities.Topico;
import br.com.forumhub.demo.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public TopicoResponseDTO criarTopico(TopicoRegisterDTO dto){

        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com este título e mensagem.");
        }

        var usuario = usuarioService.buscarUsuarioPorId(dto.autorId());
        var topico = new Topico(dto.titulo(), dto.mensagem(), usuario);

        topicoRepository.save(topico);
        return new TopicoResponseDTO(topico);
    }

    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    public Page<Topico> buscarTopicosPorData(LocalDate data, Pageable pageable) {
        return topicoRepository.findByDataCriacao(data, pageable);
    }

    public Topico buscarTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico com ID " + id + " não foi encontrado."));
    }

    public Topico atualizarTopico(Long id, @Valid TopicoUpdateDTO dto) {
        var topico = buscarTopicoPorId(id);

        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());

        return topicoRepository.save(topico);
    }

    public void deletarTopico(Long usuarioId, Long topicoid) {
        var usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        var topico = buscarTopicoPorId(topicoid);

        if (!usuario.getId().equals(topico.getAutor().getId())) {
            throw new UnauthorizedException("Usuário não autorizado a deletar este tópico.");
        }



        topicoRepository.delete(topico);
    }

}
