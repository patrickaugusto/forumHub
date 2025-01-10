package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dtos.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.dtos.topico.TopicoCreateDTO;
import br.com.forumhub.demo.dtos.topico.TopicoResponseDTO;
import br.com.forumhub.demo.model.Curso;
import br.com.forumhub.demo.model.Topico;
import br.com.forumhub.demo.model.Usuario;
import br.com.forumhub.demo.repository.CursoRepository;
import br.com.forumhub.demo.repository.TopicoRepository;
import br.com.forumhub.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public TopicoResponseDTO cadastroTopico(TopicoCreateDTO topicoDto) {
        if(topicoRepository.existsByTituloAndMensagem(topicoDto.titulo(), topicoDto.mensagem())){
            throw new RuntimeException("Titulo Ja cadastrado");
        }

        Usuario autor = usuarioRepository.findById(topicoDto.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(topicoDto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Topico topico = new Topico(topicoDto, autor, curso);
        topicoRepository.save(topico);

        return new TopicoResponseDTO(topico);
    }

    public List<TopicoResponseDTO> listarTopicos() {
        return topicoRepository.findAll()
                .stream()
                .map(TopicoResponseDTO::new)
                .collect(Collectors.toList());

    }

    public List<TopicoResponseDTO> buscarPorCursoEAno(String cursoNome, int ano){
        List<Topico> topicos = topicoRepository.findByCursoAndAno(cursoNome, ano);
        return topicos.stream()
                .map(TopicoResponseDTO::new)
                .toList();

    }

    public TopicoResponseDTO buscarPorId(Long id){
        var topico = topicoRepository.getReferenceById(id);
        return new TopicoResponseDTO(topico);
    }

    public TopicoResponseDTO atualizarTopico(Long id, TopicoUpdateDTO topicoDto){
        Optional<Topico> topicoOp = topicoRepository.findById(id);
        if(topicoOp.isEmpty()){
            throw new RuntimeException("Topico com o id " + id + " não encontrado");
        }

        var topico = topicoOp.get();

        topico = new Topico(topicoDto);
        topicoRepository.save(topico);
        return new TopicoResponseDTO(topico);
    }

    public void deleteTopico(Long id){
        topicoRepository.deleteById(id);
    }
}
