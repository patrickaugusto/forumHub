package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.resposta.RespostaRequestDTO;
import br.com.forumhub.demo.dto.resposta.RespostaResponseDTO;
import br.com.forumhub.demo.dto.resposta.RespostaUpdateDTO;
import br.com.forumhub.demo.exceptions.NotFoundException;
import br.com.forumhub.demo.exceptions.UnauthorizedException;
import br.com.forumhub.demo.model.entities.Resposta;
import br.com.forumhub.demo.model.entities.Topico;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.RespostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RespostaServiceTest {

    @InjectMocks
    private RespostaService respostaService;

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private TopicoService topicoService;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarResposta_deveSalvarResposta() {
        // Arrange
        RespostaRequestDTO dto = new RespostaRequestDTO("Mensagem", 1L, 2L);
        Topico topico = new Topico();
        topico.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        Resposta resposta = new Resposta("Mensagem", topico, usuario);

        when(topicoService.buscarTopicoPorId(dto.topicoId())).thenReturn(topico);
        when(usuarioService.buscarUsuarioPorId(dto.usuarioId())).thenReturn(usuario);
        when(respostaRepository.save(any(Resposta.class))).thenReturn(resposta);

        // Act
        Resposta result = respostaService.criarResposta(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Mensagem", result.getMensagem());
        verify(respostaRepository, times(1)).save(any(Resposta.class));
    }

    @Test
    void listarRespostas_deveRetornarPaginaDeRespostas() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Resposta resposta = new Resposta();
        Page<Resposta> pagina = new PageImpl<>(List.of(resposta));

        when(respostaRepository.findAll(pageable)).thenReturn(pagina);

        // Act
        Page<RespostaResponseDTO> result = respostaService.listarRespostas(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(respostaRepository, times(1)).findAll(pageable);
    }

    @Test
    void buscarPorId_deveRetornarResposta() {
        // Arrange
        Resposta resposta = new Resposta();
        resposta.setId(1L);

        when(respostaRepository.findById(1L)).thenReturn(Optional.of(resposta));

        // Act
        Resposta result = respostaService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(respostaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_deveLancarNotFoundException() {
        // Arrange
        when(respostaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> respostaService.buscarPorId(1L));
        verify(respostaRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarResposta_deveAtualizarMensagem() {
        // Arrange
        Resposta resposta = new Resposta();
        resposta.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        resposta.setUsuario(usuario);

        RespostaUpdateDTO dto = new RespostaUpdateDTO("Nova Mensagem");

        when(respostaRepository.findById(1L)).thenReturn(Optional.of(resposta));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuario);
        when(respostaRepository.save(any(Resposta.class))).thenReturn(resposta);

        // Act
        Resposta result = respostaService.atualizarResposta(1L, 2L, dto);

        // Assert
        assertNotNull(result);
        assertEquals("Nova Mensagem", result.getMensagem());
        verify(respostaRepository, times(1)).save(resposta);
    }

    @Test
    void atualizarResposta_deveLancarUnauthorizedException() {
        // Arrange
        Resposta resposta = new Resposta();
        resposta.setId(1L);
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(3L);
        resposta.setUsuario(outroUsuario);

        Usuario usuarioAtual = new Usuario();
        usuarioAtual.setId(2L);
        RespostaUpdateDTO dto = new RespostaUpdateDTO("Nova Mensagem");

        when(respostaRepository.findById(1L)).thenReturn(Optional.of(resposta));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuarioAtual);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> respostaService.atualizarResposta(1L, 2L, dto));
    }

    @Test
    void deletarResposta_deveDeletarResposta() {
        // Arrange
        Resposta resposta = new Resposta();
        resposta.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        resposta.setUsuario(usuario);

        when(respostaRepository.findById(1L)).thenReturn(Optional.of(resposta));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuario);

        // Act
        respostaService.deletarResposta(1L, 2L);

        // Assert
        verify(respostaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarResposta_deveLancarUnauthorizedException() {
        // Arrange
        Resposta resposta = new Resposta();
        resposta.setId(1L);
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(3L);
        resposta.setUsuario(outroUsuario);

        Usuario usuarioAtual = new Usuario();
        usuarioAtual.setId(2L);

        when(respostaRepository.findById(1L)).thenReturn(Optional.of(resposta));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuarioAtual);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> respostaService.deletarResposta(1L, 2L));
    }
}
