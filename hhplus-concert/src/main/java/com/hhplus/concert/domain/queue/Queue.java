package com.hhplus.concert.domain.queue;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "queues")
@NoArgsConstructor
@Getter
@Setter
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String status; // 대기열 상태 (WAITING, PROGRESS, DONE, EXPIRED)

    @Column(nullable = false)
    private int queuePosition;

    @Column(nullable = false)
    private LocalDateTime enteredAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @PrePersist
    public void prePersist() {
        this.enteredAt = this.enteredAt == null ? LocalDateTime.now() : this.enteredAt;
        this.expiredAt = this.expiredAt == null ? LocalDateTime.now().plusMinutes(5) : this.expiredAt;
    }

    // 생성자
    public Queue(UUID userId, String token, String status, LocalDateTime enteredAt, LocalDateTime expiredAt, int queuePosition) {
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.enteredAt = enteredAt;
        this.expiredAt = expiredAt;
        this.queuePosition = queuePosition;
    }
}
