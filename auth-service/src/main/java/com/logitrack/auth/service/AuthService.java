package com.logitrack.auth.service;

import com.logitrack.auth.dto.*;
import com.logitrack.auth.entity.Usuario;
import com.logitrack.auth.repository.UsuarioRepository;
import com.logitrack.auth.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService        jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService        = jwtService;
    }

    public AuthResponse registrar(RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.email()))
            throw new RuntimeException("Email já cadastrado.");

        String hash = encoder.encode(req.senha());
        Usuario u = new Usuario(req.email(), hash, req.nome(), req.role());
        usuarioRepository.save(u);
        String token = jwtService.gerarToken(u.getEmail(), u.getRole().name());
        return new AuthResponse(token, u.getEmail(), u.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        Usuario u = usuarioRepository.findByEmail(req.email())
            .orElseThrow(() -> new RuntimeException("Credenciais inválidas."));

        if (!encoder.matches(req.senha(), u.getSenha()))
            throw new RuntimeException("Credenciais inválidas.");

        String token = jwtService.gerarToken(u.getEmail(), u.getRole().name());
        return new AuthResponse(token, u.getEmail(), u.getRole().name());
    }
}
