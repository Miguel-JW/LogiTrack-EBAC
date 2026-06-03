package com.logitrack.delivery.service;

import com.logitrack.delivery.entity.*;
import com.logitrack.delivery.kafka.EntregaEventProducer;
import com.logitrack.delivery.repository.EntregaRepository;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    private final EntregaRepository    entregaRepository;
    private final EntregaEventProducer producer;

    public EntregaService(EntregaRepository entregaRepository,
                          EntregaEventProducer producer) {
        this.entregaRepository = entregaRepository;
        this.producer          = producer;
    }

    public Entrega atribuir(Long pedidoId, String entregadorEmail) {
        Entrega entrega = new Entrega(pedidoId, entregadorEmail);
        entregaRepository.save(entrega);
        producer.publicarAssigned(pedidoId);
        return entrega;
    }

    public Entrega atualizarStatus(Long id, String entregadorEmail, StatusEntrega novoStatus) {
        Entrega entrega = entregaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada."));

        if (!entrega.getEntregadorEmail().equals(entregadorEmail))
            throw new RuntimeException("Entregador não autorizado.");

        validarTransicao(entrega.getStatus(), novoStatus);
        entrega.setStatus(novoStatus);
        entregaRepository.save(entrega);

        if (novoStatus == StatusEntrega.ENTREGUE)
            producer.publicarCompleted(entrega.getPedidoId());
        else
            producer.publicarUpdated(entrega.getPedidoId(), novoStatus.name());

        return entrega;
    }

    // Impede pular status
    private void validarTransicao(StatusEntrega atual, StatusEntrega novo) {
        boolean valido = switch (atual) {
            case COLETADO  -> novo == StatusEntrega.EM_ROTA;
            case EM_ROTA   -> novo == StatusEntrega.ENTREGUE || novo == StatusEntrega.CANCELADO;
            default        -> false;
        };
        if (!valido) throw new RuntimeException(
            "Transição inválida: " + atual + " → " + novo);
    }
}
