package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.usuario.UsuarioRegisterDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioUpdateDTO;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario adicionarUsuario(UsuarioRegisterDTO dto) {

        if (usuarioRepository.findByEmail(dto.email()) != null) {
            throw new RuntimeException("Usuário já existe.");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario(dto.nome(), dto.email(), senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        UserDetails userDetails = usuarioRepository.findByEmail(email);

        if (userDetails == null) {
            throw new RuntimeException("Usuario não encontrado");
        }

        return (Usuario) userDetails;
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não foi encontrado."));
    }

    public Usuario atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        if (usuarioOp.isEmpty()) {
            throw new RuntimeException();
        }
        Usuario usuario = usuarioOp.get();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);
        if (usuarioOp.isEmpty()) {
            throw new RuntimeException();
        }
        Usuario usuario = usuarioOp.get();
        usuarioRepository.delete(usuario);
    }
}
