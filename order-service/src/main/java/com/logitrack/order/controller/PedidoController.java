package com.logitrack.order.controller;

import com.logitrack.order.dto.*;
import com.logitrack.order.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gestão de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar pedido (CLIENTE)")
    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @RequestHeader("X-User-Email") String email,
            @RequestBody PedidoRequest req) {
        return ResponseEntity.status(201).body(pedidoService.criar(email, req));
    }

    @Operation(summary = "Listar meus pedidos (CLIENTE)")
    @GetMapping("/meus")
    public ResponseEntity<List<PedidoResponse>> meusPedidos(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(email));
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @Operation(summary = "Cancelar pedido (ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        pedidoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
