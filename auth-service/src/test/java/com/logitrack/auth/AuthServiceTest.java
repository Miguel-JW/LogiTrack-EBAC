package com.logitrack.auth;

import com.logitrack.auth.dto.*;
import com.logitrack.auth.entity.*;
import com.logitrack.auth.repository.UsuarioRepository;
import com.logitrack.auth.security.JwtService;
import com.logitrack.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UsuarioRepository usuarioRepository;
    @Mock JwtService        jwtService;
    @InjectMocks AuthService authService;

    @Test
    void deveRegistrarUsuarioComSucesso() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(jwtService.gerarToken(any(), any())).thenReturn("token-mock");

        RegisterRequest req = new RegisterRequest("Miguel", "miguel@email.com", "senha123", Role.CLIENTE);
        AuthResponse res = authService.registrar(req);

        assertEquals("token-mock", res.token());
        assertEquals("CLIENTE",    res.role());
    }

    @Test
    void deveLancarExcecaoQuandoEmailDuplicado() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(true);
        RegisterRequest req = new RegisterRequest("Ana", "ana@email.com", "123", Role.CLIENTE);
        assertThrows(RuntimeException.class, () -> authService.registrar(req));
    }

    @Test
    void deveLancarExcecaoComCredenciaisInvalidas() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
            () -> authService.login(new LoginRequest("x@x.com", "errada")));
    }
}
