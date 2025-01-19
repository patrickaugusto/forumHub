package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.usuario.UsuarioResponseDTO;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO buscarUsuarioPorEmail(String email) {
        UserDetails userDetails = usuarioRepository.findByEmail(email);

        if (userDetails == null) {
            throw new RuntimeException("Usuario não encontrado");
        }

        Usuario usuario = (Usuario) userDetails;
        return new UsuarioResponseDTO(usuario);
    }
}
