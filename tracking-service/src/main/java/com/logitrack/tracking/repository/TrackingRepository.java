package com.logitrack.tracking.repository;

import com.logitrack.tracking.entity.TrackingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TrackingRepository extends MongoRepository<TrackingEvent, String> {
    List<TrackingEvent> findByPedidoIdOrderByRegistradoEmAsc(Long pedidoId);
}
