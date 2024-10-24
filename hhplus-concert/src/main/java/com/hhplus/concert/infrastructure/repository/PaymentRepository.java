package com.hhplus.concert.infrastructure.repository;


import com.hhplus.concert.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(UUID userId);
    List<Payment> findBySeatId(Long seatId);
    Payment save(Payment payment);
}