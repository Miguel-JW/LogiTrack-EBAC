package com.logitrack.delivery.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @KafkaListener(topics = "order-created", groupId = "delivery-group")
    public void consumirPedidoCriado(String pedidoId) {
        System.out.println("📦 Novo pedido recebido para entrega: " + pedidoId);
        // Aqui a lógica de atribuição de entregador seria acionada
    }
}
