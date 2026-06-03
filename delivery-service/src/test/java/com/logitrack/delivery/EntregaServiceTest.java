package com.logitrack.delivery;

import com.logitrack.delivery.entity.*;
import com.logitrack.delivery.kafka.EntregaEventProducer;
import com.logitrack.delivery.repository.EntregaRepository;
import com.logitrack.delivery.service.EntregaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock EntregaRepository    entregaRepository;
    @Mock EntregaEventProducer producer;
    @InjectMocks EntregaService entregaService;

    @Test
    void deveLancarExcecaoParaTransicaoInvalida() {
        Entrega e = new Entrega(1L, "entregador@email.com");
        e.setStatus(StatusEntrega.COLETADO);
        when(entregaRepository.findById(1L)).thenReturn(Optional.of(e));

        // Tentar pular direto para ENTREGUE sem passar por EM_ROTA
        assertThrows(RuntimeException.class,
            () -> entregaService.atualizarStatus(1L, "entregador@email.com", StatusEntrega.ENTREGUE));
    }

    @Test
    void deveLancarExcecaoParaEntregadorNaoAutorizado() {
        Entrega e = new Entrega(1L, "certo@email.com");
        when(entregaRepository.findById(1L)).thenReturn(Optional.of(e));

        assertThrows(RuntimeException.class,
            () -> entregaService.atualizarStatus(1L, "errado@email.com", StatusEntrega.EM_ROTA));
    }
}
