package com.logitrack.order;

import com.logitrack.order.dto.*;
import com.logitrack.order.entity.*;
import com.logitrack.order.kafka.PedidoEventProducer;
import com.logitrack.order.repository.PedidoRepository;
import com.logitrack.order.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock PedidoRepository    pedidoRepository;
    @Mock PedidoEventProducer producer;
    @InjectMocks PedidoService pedidoService;

    @Test
    void deveCriarPedidoComSucesso() {
        when(pedidoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        PedidoResponse res = pedidoService.criar("cliente@email.com",
            new PedidoRequest("Rua A, 1", "Rua B, 2"));
        assertNotNull(res);
        assertEquals(StatusPedido.CRIADO, res.status());
        verify(producer).publicarPedidoCriado(any());
    }

    @Test
    void deveLancarExcecaoAoCancelarPedidoEmRota() {
        Pedido p = new Pedido("c@c.com", "A", "B");
        p.setStatus(StatusPedido.EM_ROTA);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(p));
        assertThrows(RuntimeException.class, () -> pedidoService.cancelar(1L));
    }
}
