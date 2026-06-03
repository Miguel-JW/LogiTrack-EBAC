package com.logitrack.order.dto;

import com.logitrack.order.entity.Pedido;
import com.logitrack.order.entity.StatusPedido;
import java.time.LocalDateTime;

public record PedidoResponse(
    Long id, String clienteEmail,
    String enderecoOrigem, String enderecoDestino,
    StatusPedido status, LocalDateTime criadoEm) {

    public static PedidoResponse de(Pedido p) {
        return new PedidoResponse(p.getId(), p.getClienteEmail(),
            p.getEnderecoOrigem(), p.getEnderecoDestino(),
            p.getStatus(), p.getCriadoEm());
    }
}
