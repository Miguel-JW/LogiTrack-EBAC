package com.logitrack.delivery.repository;

import com.logitrack.delivery.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByEntregadorEmail(String email);
}
