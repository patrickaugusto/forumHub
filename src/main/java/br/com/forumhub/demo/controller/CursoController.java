package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dtos.curso.CursoCreateDTO;
import br.com.forumhub.demo.dtos.curso.CursoResponseDTO;
import br.com.forumhub.demo.dtos.curso.CursoUpdateDTO;
import br.com.forumhub.demo.model.Curso;
import br.com.forumhub.demo.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criarCurso(@RequestBody @Valid CursoCreateDTO cursoCreateDTO) {
        CursoResponseDTO cursoCriado = cursoService.criarCurso(cursoCreateDTO);
        return ResponseEntity.ok(cursoCriado);
    }


    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        List<CursoResponseDTO> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarCursoPorId(@PathVariable Long id) {
        var curso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> atualizarCurso(
            @PathVariable Long id,
            @RequestBody @Valid CursoUpdateDTO cursoUpdateDTO) {
        CursoResponseDTO cursoAtualizado = cursoService.atualizarCurso(id, cursoUpdateDTO);
        return ResponseEntity.ok(cursoAtualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

