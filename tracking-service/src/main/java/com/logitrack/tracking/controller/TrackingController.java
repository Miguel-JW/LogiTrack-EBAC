package com.logitrack.tracking.controller;

import com.logitrack.tracking.entity.TrackingEvent;
import com.logitrack.tracking.repository.TrackingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking")
@Tag(name = "Tracking", description = "Histórico de entregas")
public class TrackingController {

    private final TrackingRepository trackingRepository;

    public TrackingController(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    @Operation(summary = "Histórico completo de um pedido")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<List<TrackingEvent>> historico(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(
            trackingRepository.findByPedidoIdOrderByRegistradoEmAsc(pedidoId));
    }
}
