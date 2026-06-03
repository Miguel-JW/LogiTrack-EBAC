package com.logitrack.tracking.kafka;

import com.logitrack.tracking.entity.TrackingEvent;
import com.logitrack.tracking.repository.TrackingRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TrackingConsumer {

    private final TrackingRepository trackingRepository;

    public TrackingConsumer(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    @KafkaListener(topics = "order-created", groupId = "tracking-group")
    public void onOrderCreated(String pedidoId) {
        trackingRepository.save(new TrackingEvent(
            Long.parseLong(pedidoId), "CRIADO", "Pedido criado pelo cliente."));
    }

    @KafkaListener(topics = "delivery-assigned", groupId = "tracking-group")
    public void onDeliveryAssigned(String pedidoId) {
        trackingRepository.save(new TrackingEvent(
            Long.parseLong(pedidoId), "COLETADO", "Entregador atribuído."));
    }

    @KafkaListener(topics = "delivery-updated", groupId = "tracking-group")
    public void onDeliveryUpdated(String payload) {
        String[] parts = payload.split(":");
        trackingRepository.save(new TrackingEvent(
            Long.parseLong(parts[0]), parts[1], "Status atualizado para " + parts[1]));
    }

    @KafkaListener(topics = "delivery-completed", groupId = "tracking-group")
    public void onDeliveryCompleted(String pedidoId) {
        trackingRepository.save(new TrackingEvent(
            Long.parseLong(pedidoId), "ENTREGUE", "Entrega concluída com sucesso."));
    }
}
