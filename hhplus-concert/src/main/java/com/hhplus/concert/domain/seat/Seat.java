package com.hhplus.concert.domain.seat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "seats")
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertScheduleId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private String seatStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        validatePosition();
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
        this.updatedAt = this.updatedAt == null ? LocalDateTime.now() : this.updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Seat(Long concertScheduleId, Long amount, Integer position, String seatStatus) {
        this.concertScheduleId = concertScheduleId;
        this.amount = amount;
        this.position = position;
        this.seatStatus = seatStatus;
        validatePosition();
    }

    // 좌석 번호가 1에서 50 사이인지 확인
    private void validatePosition() {
        if (position < 1 || position > 50) {
            throw new IllegalArgumentException("좌석 번호는 1부터 50 사이여야 합니다.");
        }
    }
}
