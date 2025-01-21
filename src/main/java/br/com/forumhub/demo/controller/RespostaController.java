package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dto.resposta.RespostaRequestDTO;
import br.com.forumhub.demo.dto.resposta.RespostaResponseDTO;
import br.com.forumhub.demo.dto.resposta.RespostaUpdateDTO;
import br.com.forumhub.demo.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping

    public ResponseEntity<RespostaResponseDTO> criarResposta(@RequestBody @Valid RespostaRequestDTO dto) {
        var resposta = respostaService.criarResposta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RespostaResponseDTO(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<RespostaResponseDTO>> listarRespostas(Pageable pageable) {
        return ResponseEntity.ok(respostaService.listarRespostas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(respostaService.buscarPorId(id));
    }

    @PutMapping("/{respostaId}/{usuarioId}")
    public ResponseEntity<RespostaResponseDTO> atualizarResposta(@PathVariable
                                                                 Long respostaId,
                                                                 @PathVariable
                                                                 Long usuarioId,
                                                                 @RequestBody @Valid
                                                                 RespostaUpdateDTO dto) {
        var resposta = respostaService.atualizarResposta(respostaId, usuarioId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new RespostaResponseDTO(resposta));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResposta(@PathVariable Long id) {
        respostaService.deletarResposta(id);
        return ResponseEntity.noContent().build();
    }
}