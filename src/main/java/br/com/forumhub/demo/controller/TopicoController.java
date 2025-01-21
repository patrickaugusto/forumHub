package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.exceptions.UnauthorizedException;
import br.com.forumhub.demo.dto.topico.TopicoRegisterDTO;
import br.com.forumhub.demo.dto.topico.TopicoResponseDTO;
import br.com.forumhub.demo.dto.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping("/add")
    public ResponseEntity<TopicoResponseDTO> cadastrarTopico(@RequestBody @Valid TopicoRegisterDTO dto) {
        var dtoTopico = topicoService.criarTopico(dto);
        return ResponseEntity.ok().body(dtoTopico);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        var topicoPag = topicoService.listarTopicos(pageable);
        var topicoDtoPag = topicoPag.map(TopicoResponseDTO::new);

        return ResponseEntity.ok().body(topicoDtoPag);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<Page<TopicoResponseDTO>> buscarTopicosPorData(
            @RequestParam("data") LocalDate data,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {
        var topicoPag = topicoService.buscarTopicosPorData(data, pageable);
        var topicoDtoPag = topicoPag.map(TopicoResponseDTO::new);
        return ResponseEntity.ok().body(topicoDtoPag);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TopicoResponseDTO> buscarTopicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(new TopicoResponseDTO(topicoService.buscarTopicoPorId(id)));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TopicoResponseDTO> atualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDTO dto) {
        return ResponseEntity.ok().body(new TopicoResponseDTO(topicoService.atualizarTopico(id, dto)));
    }

    @DeleteMapping("/deletar/{usuarioId}/{topicoId}")
    public ResponseEntity deletarTopico(@PathVariable Long usuarioId, @PathVariable Long topicoId) {
        try {
            topicoService.deletarTopico(usuarioId, topicoId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
