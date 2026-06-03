package com.logitrack.delivery.controller;

import com.logitrack.delivery.entity.*;
import com.logitrack.delivery.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
@Tag(name = "Entregas", description = "Gestão de entregas")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @Operation(summary = "Atribuir entregador a pedido (ADMIN)")
    @PostMapping("/atribuir/{pedidoId}")
    public ResponseEntity<Entrega> atribuir(
            @PathVariable Long pedidoId,
            @RequestParam String entregadorEmail) {
        return ResponseEntity.status(201).body(entregaService.atribuir(pedidoId, entregadorEmail));
    }

    @Operation(summary = "Atualizar status da entrega (ENTREGADOR)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Entrega> atualizarStatus(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email,
            @RequestParam StatusEntrega status) {
        return ResponseEntity.ok(entregaService.atualizarStatus(id, email, status));
    }
}
