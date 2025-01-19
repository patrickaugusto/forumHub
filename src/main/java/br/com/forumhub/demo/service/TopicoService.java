package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.topico.TopicoRegisterDTO;
import br.com.forumhub.demo.dto.topico.TopicoResponseDTO;
import br.com.forumhub.demo.dto.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.model.entities.Topico;
import br.com.forumhub.demo.repository.TopicoRepository;
import br.com.forumhub.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TopicoResponseDTO criarTopico(TopicoRegisterDTO dto){

        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com este título e mensagem.");
        }

        var usuario = usuarioRepository.getReferenceById(dto.autorId());
        var topico = new Topico(dto.titulo(), dto.mensagem(), usuario);

        topicoRepository.save(topico);
        return new TopicoResponseDTO(topico);
    }

    public Page<TopicoResponseDTO> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(TopicoResponseDTO::new);
    }

    public Page<TopicoResponseDTO> buscarTopicosPorData(LocalDate data, Pageable pageable) {
        return topicoRepository.findByDataCriacao(data, pageable).map(TopicoResponseDTO::new);
    }

    public TopicoResponseDTO buscarTopicoPorId(Long id) {
        return new TopicoResponseDTO(buscarPorId(id));
    }

    public TopicoResponseDTO atualizarTopico(Long id, @Valid TopicoUpdateDTO dto) {
        var topico = buscarPorId(id);

        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());

        topicoRepository.save(topico);

        return new TopicoResponseDTO(topico);
    }

    public void deletarTopico(Long id) {
        var topico = buscarPorId(id);
        topicoRepository.delete(topico);
    }

    private Topico buscarPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico com o id " + id + " não encontrado."));
    }
}
