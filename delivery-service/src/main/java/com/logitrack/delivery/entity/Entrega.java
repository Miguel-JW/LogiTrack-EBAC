package com.logitrack.delivery.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private Long   pedidoId;
    private String entregadorEmail;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    private LocalDateTime atualizadoEm;

    public Entrega() {}

    public Entrega(Long pedidoId, String entregadorEmail) {
        this.pedidoId        = pedidoId;
        this.entregadorEmail = entregadorEmail;
        this.status          = StatusEntrega.COLETADO;
        this.atualizadoEm    = LocalDateTime.now();
    }

    public Long          getId()              { return id; }
    public Long          getPedidoId()        { return pedidoId; }
    public String        getEntregadorEmail() { return entregadorEmail; }
    public StatusEntrega getStatus()          { return status; }
    public LocalDateTime getAtualizadoEm()   { return atualizadoEm; }

    public void setStatus(StatusEntrega status) {
        this.status       = status;
        this.atualizadoEm = LocalDateTime.now();
    }
}
