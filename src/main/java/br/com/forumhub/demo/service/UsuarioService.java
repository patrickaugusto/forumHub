package br.com.forumhub.demo.service;
import br.com.forumhub.demo.dtos.usuario.UsuarioCreateDTO;
import br.com.forumhub.demo.dtos.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.dtos.usuario.UsuarioUpdateDTO;
import br.com.forumhub.demo.model.Usuario;
import br.com.forumhub.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar um usuário
    public UsuarioResponseDTO criarUsuario(@Valid UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario(usuarioCreateDTO);
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return new UsuarioResponseDTO(usuario.get());
        }
        throw new RuntimeException("Usuario não encontrado!");
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }


    public UsuarioResponseDTO atualizarUsuario(Long id, @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.getEmail().equals(usuarioUpdateDTO.email())
                && usuarioRepository.existsByEmail(usuarioUpdateDTO.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        usuario = new Usuario(usuarioUpdateDTO);

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuario);
    }


}

