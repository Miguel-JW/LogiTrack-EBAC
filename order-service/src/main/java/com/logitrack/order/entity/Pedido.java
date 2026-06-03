package com.logitrack.order.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    @Column(nullable = false)
    private String clienteEmail;

    @Column(nullable = false)
    private String enderecoOrigem;

    @Column(nullable = false)
    private String enderecoDestino;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Pedido() {}

    public Pedido(String clienteEmail, String enderecoOrigem, String enderecoDestino) {
        this.clienteEmail    = clienteEmail;
        this.enderecoOrigem  = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.status          = StatusPedido.CRIADO;
        this.criadoEm        = LocalDateTime.now();
        this.atualizadoEm    = LocalDateTime.now();
    }

    public Long          getId()               { return id; }
    public String        getClienteEmail()     { return clienteEmail; }
    public String        getEnderecoOrigem()   { return enderecoOrigem; }
    public String        getEnderecoDestino()  { return enderecoDestino; }
    public StatusPedido  getStatus()           { return status; }
    public LocalDateTime getCriadoEm()         { return criadoEm; }
    public LocalDateTime getAtualizadoEm()     { return atualizadoEm; }

    public void setStatus(StatusPedido status) {
        this.status       = status;
        this.atualizadoEm = LocalDateTime.now();
    }
}
