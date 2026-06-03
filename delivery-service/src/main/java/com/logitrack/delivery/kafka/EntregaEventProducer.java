package com.logitrack.delivery.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EntregaEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EntregaEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarAssigned(Long pedidoId) {
        kafkaTemplate.send("delivery-assigned", String.valueOf(pedidoId));
    }

    public void publicarUpdated(Long pedidoId, String status) {
        kafkaTemplate.send("delivery-updated", pedidoId + ":" + status);
    }

    public void publicarCompleted(Long pedidoId) {
        kafkaTemplate.send("delivery-completed", String.valueOf(pedidoId));
    }
}
