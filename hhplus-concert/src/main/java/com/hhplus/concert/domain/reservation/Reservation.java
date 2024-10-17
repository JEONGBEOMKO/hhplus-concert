package com.hhplus.concert.domain.reservation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long seatAmount;

    @Column(nullable = false)
    private Integer seatPosition;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime reservedAt;

    @Column(nullable = false)
    private LocalDateTime reservedExpiresAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
        this.reservedAt = this.reservedAt == null ? LocalDateTime.now() : this.reservedAt;
        this.reservedExpiresAt = this.reservedExpiresAt == null ? LocalDateTime.now().plusMinutes(5) : this.reservedExpiresAt;
    }

    public Reservation(UUID userId, Long seatId, Long seatAmount, Integer seatPosition, String status) {
        this.userId = userId;
        this.seatId = seatId;
        this.seatAmount = seatAmount;
        this.seatPosition = seatPosition;  // Integer 타입으로 일치
        this.status = status;
    }

}
