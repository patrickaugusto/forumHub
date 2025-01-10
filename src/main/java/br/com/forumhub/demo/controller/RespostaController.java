package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dtos.resposta.RespostaCreateDTO;
import br.com.forumhub.demo.dtos.resposta.RespostaResponseDTO;
import br.com.forumhub.demo.dtos.resposta.RespostaUpdateDTO;
import br.com.forumhub.demo.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<RespostaResponseDTO> criarResposta(@RequestBody @Valid RespostaCreateDTO respostaCreateDTO) {
        RespostaResponseDTO respostaCriada = respostaService.criarResposta(respostaCreateDTO);
        return ResponseEntity.ok(respostaCriada);
    }

    @GetMapping
    public ResponseEntity<List<RespostaResponseDTO>> listarRespostas() {
        List<RespostaResponseDTO> respostas = respostaService.listarTodas();
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> buscarRespostaPorId(@PathVariable Long id) {
        var resposta = respostaService.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> atualizarResposta(
            @PathVariable Long id,
            @RequestBody @Valid RespostaUpdateDTO respostaUpdateDTO) {
        RespostaResponseDTO respostaAtualizada = respostaService.atualizarResposta(id, respostaUpdateDTO);
        return ResponseEntity.ok(respostaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResposta(@PathVariable Long id) {
        respostaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

