package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dtos.DadosAtualizarTopico;
import br.com.forumhub.demo.dtos.DadosCadastroTopico;
import br.com.forumhub.demo.dtos.DadosListagemTopico;
import br.com.forumhub.demo.model.Curso;
import br.com.forumhub.demo.model.Topico;
import br.com.forumhub.demo.model.Usuario;
import br.com.forumhub.demo.repository.CursoRepository;
import br.com.forumhub.demo.repository.TopicoRepository;
import br.com.forumhub.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public DadosListagemTopico cadastroTopico(DadosCadastroTopico topicoDto) {
        if(topicoRepository.existsByTituloAndMensagem(topicoDto.titulo(), topicoDto.mensagem())){
            throw new RuntimeException("Titulo Ja cadastrado");
        }

        Usuario autor = usuarioRepository.findById(topicoDto.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(topicoDto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Topico topico = new Topico(topicoDto, autor, curso);
        topicoRepository.save(topico);

        return new DadosListagemTopico(topico);
    }

    public List<DadosListagemTopico> listarTopicos() {
        return topicoRepository.findAll()
                .stream()
                .map(DadosListagemTopico::new)
                .collect(Collectors.toList());

    }

    public List<DadosListagemTopico> buscarPorCursoEAno(String cursoNome, int ano){
        List<Topico> topicos = topicoRepository.findByCursoAndAno(cursoNome, ano);
        return topicos.stream()
                .map(DadosListagemTopico::new)
                .toList();

    }

    public DadosListagemTopico buscarPorId(Long id){
        var topico = topicoRepository.getReferenceById(id);
        return new DadosListagemTopico(topico);
    }

    public DadosListagemTopico atualizarTopico(Long id, DadosAtualizarTopico topicoDto){
        Optional<Topico> topicoOp = topicoRepository.findById(id);
        if(topicoOp.isEmpty()){
            throw new RuntimeException("Topico com o id " + id + " não encontrado");
        }

        var topico = topicoOp.get();

        topico = new Topico(topicoDto);
        return new DadosListagemTopico(topico);
    }

    public void deleteTopico(Long id){
        topicoRepository.deleteById(id);
    }
}
