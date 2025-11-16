package br.com.nicolas.aluno.academico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/painel")
    public ResponseEntity<String> painelAdmin() {
        return ResponseEntity.ok("Bem-vindo ao Painel do Administrador!");
    }
}