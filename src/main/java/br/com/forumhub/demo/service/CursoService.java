package br.com.forumhub.demo.service;
import br.com.forumhub.demo.dtos.curso.CursoCreateDTO;
import br.com.forumhub.demo.dtos.curso.CursoResponseDTO;
import br.com.forumhub.demo.dtos.curso.CursoUpdateDTO;
import br.com.forumhub.demo.model.Curso;
import br.com.forumhub.demo.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoResponseDTO criarCurso(@Valid CursoCreateDTO cursoCreateDTO) {
        if (cursoRepository.existsByNome(cursoCreateDTO.nome())) {
            throw new IllegalArgumentException("Curso com este nome já está cadastrado!");
        }
        Curso curso = cursoRepository.save(new Curso(cursoCreateDTO));
        return new CursoResponseDTO(curso);
    }

    public List<CursoResponseDTO> listarTodos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream().map(CursoResponseDTO::new).collect(Collectors.toList());
    }

    public CursoResponseDTO buscarCursoPorId(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado!"));
        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO atualizarCurso(Long id, @Valid CursoUpdateDTO cursoAtualizado) {
        Curso curso = cursoRepository.getReferenceById(id);
        if (!curso.getNome().equals(cursoAtualizado.nome())
                && cursoRepository.existsByNome(cursoAtualizado.nome())) {
            throw new IllegalArgumentException("Já existe um curso com este nome!");
        }

        curso = new Curso(cursoAtualizado);
        cursoRepository.save(curso);
        return new CursoResponseDTO(curso);
    }

    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }
}

