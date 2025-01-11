package br.com.forumhub.demo.controller;
import br.com.forumhub.demo.config.security.TokenService;
import br.com.forumhub.demo.dtos.usuario.UsuarioCreateDTO;
import br.com.forumhub.demo.dtos.usuario.UsuarioLoginDTO;
import br.com.forumhub.demo.dtos.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken dadosLogin =
                new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.senha());

        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            return ResponseEntity.ok().body(tokenService.gerarToken(authentication));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Erro ao autenticar ", e);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioCreateDTO dto) {
         var usuarioCadastrado = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }
}

