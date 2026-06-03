package com.logitrack.order.service;

import com.logitrack.order.dto.*;
import com.logitrack.order.entity.*;
import com.logitrack.order.kafka.PedidoEventProducer;
import com.logitrack.order.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository     pedidoRepository;
    private final PedidoEventProducer  producer;

    public PedidoService(PedidoRepository pedidoRepository, PedidoEventProducer producer) {
        this.pedidoRepository = pedidoRepository;
        this.producer         = producer;
    }

    public PedidoResponse criar(String clienteEmail, PedidoRequest req) {
        Pedido pedido = new Pedido(clienteEmail, req.enderecoOrigem(), req.enderecoDestino());
        pedidoRepository.save(pedido);
        producer.publicarPedidoCriado(pedido.getId());
        return PedidoResponse.de(pedido);
    }

    public List<PedidoResponse> listarPorCliente(String email) {
        return pedidoRepository.findByClienteEmail(email)
            .stream().map(PedidoResponse::de).toList();
    }

    public PedidoResponse buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .map(PedidoResponse::de)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }

    public void cancelar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        if (pedido.getStatus() != StatusPedido.CRIADO)
            throw new RuntimeException("Cancelamento permitido apenas antes da coleta.");

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
        producer.publicarPedidoCancelado(id);
    }
}
