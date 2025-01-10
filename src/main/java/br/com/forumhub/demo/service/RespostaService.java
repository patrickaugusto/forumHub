package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dtos.resposta.RespostaCreateDTO;
import br.com.forumhub.demo.dtos.resposta.RespostaResponseDTO;
import br.com.forumhub.demo.dtos.resposta.RespostaUpdateDTO;
import br.com.forumhub.demo.model.Resposta;
import br.com.forumhub.demo.repository.RespostaRepository;
import br.com.forumhub.demo.repository.TopicoRepository;
import br.com.forumhub.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepositoy;

    public RespostaResponseDTO criarResposta(@Valid RespostaCreateDTO respostaCreateDTO) {

        var topico = topicoRepository.getReferenceById(respostaCreateDTO.topicoId());
        var autor = usuarioRepositoy.getReferenceById(respostaCreateDTO.autorId());

        Resposta resposta = respostaRepository.save(new Resposta(respostaCreateDTO, topico, autor));
        return new RespostaResponseDTO(resposta);
    }

    public List<RespostaResponseDTO> listarTodas() {
        List<Resposta> respostas = respostaRepository.findAll();
        return respostas.stream().map(RespostaResponseDTO::new).collect(Collectors.toList());

    }

    public RespostaResponseDTO buscarPorId(Long id) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada!"));
        return new RespostaResponseDTO(resposta);
    }

    public void deletar(Long id) {
        Resposta resposta = respostaRepository.getReferenceById(id);
        respostaRepository.delete(resposta);
    }

    public RespostaResponseDTO atualizarResposta(Long id, @Valid RespostaUpdateDTO respostaUpdateDTO) {
        Resposta resposta = respostaRepository.getReferenceById(id);


        resposta = new Resposta(respostaUpdateDTO);
        respostaRepository.save(resposta);
        return new RespostaResponseDTO(resposta);
    }
}

