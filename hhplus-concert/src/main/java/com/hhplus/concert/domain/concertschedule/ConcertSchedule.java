package com.hhplus.concert.domain.concertschedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "concert_schedules")
@Getter
@Setter
public class ConcertSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private LocalDate openAt;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private Integer totalSeat;

    @Column(nullable = false)
    private Integer availableSeat;

    @Column(nullable = false)
    private String totalSeatStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
    }

    // 매개변수 생성자
    public ConcertSchedule(Long concertId, LocalDate openAt, LocalDateTime startAt, LocalDateTime endAt, int totalSeat, int availableSeat, String totalSeatStatus) {
        this.concertId = concertId;
        this.openAt = openAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.totalSeat = totalSeat;
        this.availableSeat = availableSeat;
        this.totalSeatStatus = totalSeatStatus;
    }
}
