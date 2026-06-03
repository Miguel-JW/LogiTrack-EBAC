package com.logitrack.tracking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "tracking_events")
public class TrackingEvent {

    @Id
    private String        id;
    private Long          pedidoId;
    private String        status;
    private String        descricao;
    private LocalDateTime registradoEm;

    public TrackingEvent() {}

    public TrackingEvent(Long pedidoId, String status, String descricao) {
        this.pedidoId     = pedidoId;
        this.status       = status;
        this.descricao    = descricao;
        this.registradoEm = LocalDateTime.now();
    }

    public String        getId()           { return id; }
    public Long          getPedidoId()     { return pedidoId; }
    public String        getStatus()       { return status; }
    public String        getDescricao()    { return descricao; }
    public LocalDateTime getRegistradoEm() { return registradoEm; }
}
