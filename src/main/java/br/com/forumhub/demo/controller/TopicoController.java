package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dtos.DadosAtualizarTopico;
import br.com.forumhub.demo.dtos.DadosCadastroTopico;
import br.com.forumhub.demo.dtos.DadosListagemTopico;
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
    public ResponseEntity<DadosListagemTopico> cadastrar(@RequestBody @Validated DadosCadastroTopico topicoDto) {
        var dto = topicoService.cadastroTopico(topicoDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DadosListagemTopico>> listarPaginados() {

        var topicos = topicoService.listarTopicos();
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DadosListagemTopico>> listarPorCursoEAno(
            @RequestParam String cursoNome,
            @RequestParam int ano) {

        var topicos = topicoService.buscarPorCursoEAno(cursoNome, ano);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemTopico> buscarPorId(@PathVariable Long id) {
        var topico = topicoService.buscarPorId(id);
        return ResponseEntity.ok().body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosListagemTopico> atualizarTopico(@PathVariable Long id, @RequestBody @Validated DadosAtualizarTopico topicoDto) {
        return ResponseEntity.ok().body(topicoService.atualizarTopico(id, topicoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarTopico(@PathVariable Long id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.ok().build();
    }
}

