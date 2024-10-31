package com.hhplus.concert.domain.payment;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36)
    private UUID userId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
    }

    public Payment(UUID userId, Long seatId, Long price, String status) {
        this.userId = userId;
        this.seatId = seatId;
        this.price = price;
        this.status = status;
    }
}
