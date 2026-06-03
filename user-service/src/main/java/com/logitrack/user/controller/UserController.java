package com.logitrack.user.controller;

import com.logitrack.user.entity.Perfil;
import com.logitrack.user.repository.PerfilRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gestão de perfis")
public class UserController {

    private final PerfilRepository perfilRepository;

    public UserController(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Operation(summary = "Criar perfil")
    @PostMapping
    public ResponseEntity<Perfil> criar(@RequestBody Perfil perfil) {
        return ResponseEntity.status(201).body(perfilRepository.save(perfil));
    }

    @Operation(summary = "Buscar meu perfil")
    @GetMapping("/me")
    public ResponseEntity<Perfil> meuPerfil(@RequestHeader("X-User-Email") String email) {
        return perfilRepository.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar perfil")
    @PutMapping("/me")
    public ResponseEntity<Perfil> atualizar(
            @RequestHeader("X-User-Email") String email,
            @RequestBody Perfil dados) {
        return perfilRepository.findByEmail(email).map(p -> {
            p.setNome(dados.getNome());
            p.setTelefone(dados.getTelefone());
            p.setEndereco(dados.getEndereco());
            return ResponseEntity.ok(perfilRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }
}
