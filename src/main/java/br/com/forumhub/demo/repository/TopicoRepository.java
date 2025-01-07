package br.com.forumhub.demo.repository;

import br.com.forumhub.demo.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :cursoNome AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByCursoAndAno(@Param("cursoNome") String cursoNome, @Param("ano") int ano);

}
