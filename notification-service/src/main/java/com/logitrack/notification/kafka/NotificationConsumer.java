package com.logitrack.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void pedidoCriado(String pedidoId) {
        System.out.println("📧 Notificação: Pedido #" + pedidoId + " criado com sucesso!");
    }

    @KafkaListener(topics = "delivery-assigned", groupId = "notification-group")
    public void entregaAtribuida(String pedidoId) {
        System.out.println("📧 Notificação: Entregador atribuído ao pedido #" + pedidoId);
    }

    @KafkaListener(topics = "delivery-updated", groupId = "notification-group")
    public void entregaAtualizada(String payload) {
        String[] parts = payload.split(":");
        System.out.println("📧 Notificação: Pedido #" + parts[0] + " → status: " + parts[1]);
    }

    @KafkaListener(topics = "delivery-completed", groupId = "notification-group")
    public void entregaConcluida(String pedidoId) {
        System.out.println("📧 Notificação: Pedido #" + pedidoId + " entregue com sucesso! 🎉");
    }
}
