package com.logitrack.order.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PedidoEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarPedidoCriado(Long pedidoId) {
        kafkaTemplate.send("order-created", String.valueOf(pedidoId));
    }

    public void publicarPedidoCancelado(Long pedidoId) {
        kafkaTemplate.send("order-cancelled", String.valueOf(pedidoId));
    }
}
