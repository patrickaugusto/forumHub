package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dtos.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.dtos.topico.TopicoCreateDTO;
import br.com.forumhub.demo.dtos.topico.TopicoResponseDTO;
import br.com.forumhub.demo.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> cadastrar(@RequestBody @Validated TopicoCreateDTO topicoDto) {
        var dto = topicoService.cadastroTopico(topicoDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TopicoResponseDTO>> listarPaginados() {

        var topicos = topicoService.listarTopicos();
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TopicoResponseDTO>> listarPorCursoEAno(
            @RequestParam String cursoNome,
            @RequestParam int ano) {

        var topicos = topicoService.buscarPorCursoEAno(cursoNome, ano);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> buscarPorId(@PathVariable Long id) {
        var topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok().body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> atualizarTopico(@PathVariable Long id, @RequestBody @Validated TopicoUpdateDTO topicoDto) {
        return ResponseEntity.ok().body(topicoService.atualizarTopico(id, topicoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarTopico(@PathVariable Long id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.ok().build();
    }
}

