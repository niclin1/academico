package br.com.nicolas.aluno.academico.controller;

import br.com.nicolas.aluno.academico.model.Aluno;
import br.com.nicolas.aluno.academico.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/criar")
    @PreAuthorize("hasRole('ADMIN')") // Apenas usuários com ROLE_ADMIN podem acessar
    public ResponseEntity<Aluno> salvar(@RequestBody Aluno aluno) {
        Aluno salvo = alunoService.salvar(aluno);
        return ResponseEntity.created(URI.create("/api/alunos/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas usuários com ROLE_ADMIN podem acessar
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id,  @RequestBody Aluno aluno) {
        Aluno atualizado = alunoService.atualizar(id, aluno);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas usuários com ROLE_ADMIN podem acessar
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}