package br.com.forumhub.demo.controller;

import br.com.forumhub.demo.config.security.TokenService;
import br.com.forumhub.demo.dto.usuario.LoginResponseDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioLoginDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioRegisterDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.UsuarioRepository;
import br.com.forumhub.demo.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UsuarioLoginDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            UsuarioResponseDTO usuarioDto = usuarioService.buscarUsuarioPorEmail(data.email());
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok().body(new LoginResponseDTO(usuarioDto.id(), token));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioRegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe.");
        }

        String senhaCriptografada = passwordEncoder.encode(data.senha());
        Usuario usuario = new Usuario(data.nome(), data.email(), senhaCriptografada);
        this.repository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso.");
    }

}