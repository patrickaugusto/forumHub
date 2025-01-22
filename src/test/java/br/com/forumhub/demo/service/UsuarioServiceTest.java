package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.usuario.UsuarioRegisterDTO;
import br.com.forumhub.demo.dto.usuario.UsuarioUpdateDTO;
import br.com.forumhub.demo.exceptions.NotFoundException;
import br.com.forumhub.demo.exceptions.UsuarioAlreadyExistsException;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarUsuario_deveSalvarUsuario() {
        // Arrange
        UsuarioRegisterDTO dto = new UsuarioRegisterDTO("Nome", "email@example.com", "senha123");
        Usuario usuario = new Usuario(dto.nome(), dto.email(), "senhaCriptografada");

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(null);
        when(passwordEncoder.encode(dto.senha())).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.adicionarUsuario(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.nome(), result.getNome());
        assertEquals(dto.email(), result.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void adicionarUsuario_deveLancarUsuarioAlreadyExistsException() {
        // Arrange
        UsuarioRegisterDTO dto = new UsuarioRegisterDTO("Nome", "email@example.com", "senha123");
        Usuario usuarioExistente = new Usuario("Nome", "email@example.com", "senhaCriptografada");

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(usuarioExistente);

        // Act & Assert
        assertThrows(UsuarioAlreadyExistsException.class, () -> usuarioService.adicionarUsuario(dto));
    }

    @Test
    void buscarUsuarioPorEmail_deveRetornarUsuario() {
        // Arrange
        String email = "email@example.com";
        Usuario usuario = new Usuario("Nome", email, "senhaCriptografada");

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.buscarUsuarioPorEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void buscarUsuarioPorEmail_deveLancarNotFoundException() {
        // Arrange
        String email = "email@example.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> usuarioService.buscarUsuarioPorEmail(email));
    }

    @Test
    void buscarUsuarioPorId_deveRetornarUsuario() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario("Nome", "email@example.com", "senhaCriptografada");
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario result = usuarioService.buscarUsuarioPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void buscarUsuarioPorId_deveLancarNotFoundException() {
        // Arrange
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> usuarioService.buscarUsuarioPorId(id));
    }

    @Test
    void atualizarUsuario_deveAtualizarUsuario() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario("Nome", "email@example.com", "senhaCriptografada");
        usuario.setId(id);
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("Novo Nome", "novoemail@example.com");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.atualizarUsuario(id, dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.nome(), result.getNome());
        assertEquals(dto.email(), result.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deletarUsuario_deveDeletarUsuario() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario("Nome", "email@example.com", "senhaCriptografada");
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        usuarioService.deletarUsuario(id);

        // Assert
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void deletarUsuario_deveLancarNotFoundException() {
        // Arrange
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> usuarioService.deletarUsuario(id));
    }
}
