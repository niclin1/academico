package br.com.nicolas.aluno.academico.service;

import br.com.nicolas.aluno.academico.model.Aluno;
import br.com.nicolas.aluno.academico.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Transactional
    public Aluno salvar(Aluno aluno) {
        // A verificação de segurança agora é feita no Controller!
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno atualizar(Long id, Aluno aluno) {
        // A verificação de segurança agora é feita no Controller!
        Aluno existente = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        existente.setNome(aluno.getNome());
        existente.setMatricula(aluno.getMatricula());
        return alunoRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        // A verificação de segurança agora é feita no Controller!
        if (!alunoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    // O método utilitário abaixo não é mais necessário aqui
    // private boolean usuarioTemPermissao(String role) { ... }
}