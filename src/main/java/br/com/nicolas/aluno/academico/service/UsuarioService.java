package br.com.nicolas.aluno.academico.service;

import br.com.nicolas.aluno.academico.model.Usuario;
import br.com.nicolas.aluno.academico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha antes de salvar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Define um role padrão se não for fornecido
        if (usuario.getRole() == null || usuario.getRole().isEmpty()) {
            usuario.setRole("ROLE_USER");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        return usuarioRepository.findById(id).map(existente -> {
            existente.setUsername(usuario.getUsername());
            if(usuario.getPassword() != null && !usuario.getPassword().isEmpty()){
                existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            existente.setRole(usuario.getRole());
            return usuarioRepository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}