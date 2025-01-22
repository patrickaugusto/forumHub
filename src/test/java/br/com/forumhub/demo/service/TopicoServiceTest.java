package br.com.forumhub.demo.service;

import br.com.forumhub.demo.dto.topico.TopicoRegisterDTO;
import br.com.forumhub.demo.dto.topico.TopicoResponseDTO;
import br.com.forumhub.demo.dto.topico.TopicoUpdateDTO;
import br.com.forumhub.demo.exceptions.DuplicateTopicoException;
import br.com.forumhub.demo.exceptions.NotFoundException;
import br.com.forumhub.demo.exceptions.UnauthorizedException;
import br.com.forumhub.demo.model.entities.Topico;
import br.com.forumhub.demo.model.entities.Usuario;
import br.com.forumhub.demo.repository.TopicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopicoServiceTest {

    @InjectMocks
    private TopicoService topicoService;

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTopico_deveSalvarTopico() {
        // Arrange
        TopicoRegisterDTO dto = new TopicoRegisterDTO("Título", "Mensagem", 1L);
        Usuario autor = new Usuario();
        autor.setId(1L);
        Topico topico = new Topico(dto.titulo(), dto.mensagem(), autor);

        when(usuarioService.buscarUsuarioPorId(dto.autorId())).thenReturn(autor);
        when(topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())).thenReturn(false);
        when(topicoRepository.save(any(Topico.class))).thenReturn(topico);

        // Act
        TopicoResponseDTO result = topicoService.criarTopico(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.titulo(), result.titulo());
        verify(topicoRepository, times(1)).save(any(Topico.class));
    }

    @Test
    void criarTopico_deveLancarDuplicateTopicoException() {
        // Arrange
        TopicoRegisterDTO dto = new TopicoRegisterDTO("Título", "Mensagem", 1L);

        when(topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateTopicoException.class, () -> topicoService.criarTopico(dto));
    }

    @Test
    void listarTopicos_deveRetornarPaginaDeTopicos() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Topico topico = new Topico();
        Page<Topico> pagina = new PageImpl<>(List.of(topico));

        when(topicoRepository.findAll(pageable)).thenReturn(pagina);

        // Act
        Page<Topico> result = topicoService.listarTopicos(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(topicoRepository, times(1)).findAll(pageable);
    }

    @Test
    void buscarTopicosPorData_deveRetornarTopicos() {
        // Arrange
        LocalDate data = LocalDate.now();
        Pageable pageable = Pageable.unpaged();
        Topico topico = new Topico();
        Page<Topico> pagina = new PageImpl<>(List.of(topico));

        when(topicoRepository.findByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class), eq(pageable)))
                .thenReturn(pagina);

        // Act
        Page<Topico> result = topicoService.buscarTopicosPorData(data, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void buscarTopicoPorId_deveRetornarTopico() {
        // Arrange
        Topico topico = new Topico();
        topico.setId(1L);

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));

        // Act
        Topico result = topicoService.buscarTopicoPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(topicoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarTopicoPorId_deveLancarNotFoundException() {
        // Arrange
        when(topicoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> topicoService.buscarTopicoPorId(1L));
    }

    @Test
    void atualizarTopico_deveAtualizarTopico() {
        // Arrange
        Topico topico = new Topico();
        topico.setId(1L);
        Usuario autor = new Usuario();
        autor.setId(2L);
        topico.setAutor(autor);

        TopicoUpdateDTO dto = new TopicoUpdateDTO("Novo Título", "Nova Mensagem");

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(autor);
        when(topicoRepository.save(any(Topico.class))).thenReturn(topico);

        // Act
        Topico result = topicoService.atualizarTopico(1L, 2L, dto);

        // Assert
        assertNotNull(result);
        assertEquals("Novo Título", result.getTitulo());
        verify(topicoRepository, times(1)).save(topico);
    }

    @Test
    void atualizarTopico_deveLancarUnauthorizedException() {
        // Arrange
        Topico topico = new Topico();
        topico.setId(1L);
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(3L);
        topico.setAutor(outroUsuario);

        Usuario usuarioAtual = new Usuario();
        usuarioAtual.setId(2L);
        TopicoUpdateDTO dto = new TopicoUpdateDTO("Novo Título", "Nova Mensagem");

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuarioAtual);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> topicoService.atualizarTopico(1L, 2L, dto));
    }

    @Test
    void deletarTopico_deveDeletarTopico() {
        // Arrange
        Topico topico = new Topico();
        topico.setId(1L);
        Usuario autor = new Usuario();
        autor.setId(2L);
        topico.setAutor(autor);

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(autor);

        // Act
        topicoService.deletarTopico(1L, 2L);

        // Assert
        verify(topicoRepository, times(1)).delete(topico);
    }

    @Test
    void deletarTopico_deveLancarUnauthorizedException() {
        // Arrange
        Topico topico = new Topico();
        topico.setId(1L);
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(3L);
        topico.setAutor(outroUsuario);

        Usuario usuarioAtual = new Usuario();
        usuarioAtual.setId(2L);

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuarioAtual);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> topicoService.deletarTopico(1L, 2L));
    }
}
