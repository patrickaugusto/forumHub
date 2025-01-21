package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.resposta.RespostaRequestDTO;
import br.com.forumhub.demo.dto.resposta.RespostaResponseDTO;
import br.com.forumhub.demo.dto.resposta.RespostaUpdateDTO;
import br.com.forumhub.demo.exceptions.RespostaNotFoundException;
import br.com.forumhub.demo.exceptions.UnauthorizedException;
import br.com.forumhub.demo.model.entities.Resposta;
import br.com.forumhub.demo.repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private UsuarioService usuarioService;

    public Resposta criarResposta(RespostaRequestDTO dto) {
        var topico = topicoService.buscarTopicoPorId(dto.topicoId());
        var usuario = usuarioService.buscarUsuarioPorId(dto.usuarioId());

        if (!topico.getStatus().equals("ABERTO")) {
            throw new IllegalStateException("Não é possível adicionar respostas a um tópico fechado");
        }

        var resposta = new Resposta(dto.mensagem(), topico, usuario);

        return respostaRepository.save(resposta);
    }

    public Page<RespostaResponseDTO> listarRespostas(Pageable pageable) {
        return respostaRepository.findAll(pageable).map(RespostaResponseDTO::new);
    }

    public RespostaResponseDTO buscarPorId(Long id) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new RespostaNotFoundException("Não foi possivel encontrar a resposta com o id: " + id));
        return new RespostaResponseDTO(resposta);
    }

    public Resposta atualizarResposta(Long respostaId, Long usuarioId, RespostaUpdateDTO dto) {

        var resposta = respostaRepository.findById(respostaId)
                .orElseThrow(() -> new RespostaNotFoundException("Não foi possivel encontrar a resposta com o id: " + respostaId));

        var usuario = usuarioService.buscarUsuarioPorId(usuarioId);

        if (!usuario.getRespostas().contains(resposta)) {
            throw new UnauthorizedException("Usuario não pode alterar esta resposta.");
        }

        resposta.setMensagem(dto.mensagem());

        return respostaRepository.save(resposta);
    }

    public void deletarResposta(Long id) {
        if (!respostaRepository.existsById(id)) {
            throw new RespostaNotFoundException("Não foi possivel encontrar a resposta com o id: " + id);
        }
        respostaRepository.deleteById(id);
    }
}
