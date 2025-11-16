package br.com.nicolas.aluno.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nicolas.aluno.academico.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Busca exata por matrícula
    Optional<Aluno> findByMatricula(String matricula);

    // Verifica existência por matrícula (útil para validar unicidade)
    boolean existsByMatricula(String matricula);

    // Busca por nome contendo (case-insensitive)
    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    // Versão paginada da busca por nome
    Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Contagem por nome contendo (case-insensitive)
    long countByNomeContainingIgnoreCase(String nome);

    // Lista todos ordenados por nome ascendente
    List<Aluno> findAllByOrderByNomeAsc();

    @Query("""
           select a
           from Aluno a
           where (:nome is null or lower(a.nome) like lower(concat('%', :nome, '%')))
             and (:matricula is null or a.matricula = :matricula)
           """)
    Page<Aluno> search(
            @Param("nome") String nome,
            @Param("matricula") String matricula,
            Pageable pageable
    );
}