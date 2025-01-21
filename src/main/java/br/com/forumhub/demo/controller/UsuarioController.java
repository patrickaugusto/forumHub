package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.dto.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioUpdateDTO;
import br.com.forumhub.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("id/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        try{
            return ResponseEntity.ok().body(new UsuarioResponseDTO(usuarioService.buscarUsuarioPorId(id)));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            UsuarioUpdateDTO dto){
        try{
            return ResponseEntity.ok().body(new UsuarioResponseDTO(usuarioService.atualizarUsuario(id, dto)));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
