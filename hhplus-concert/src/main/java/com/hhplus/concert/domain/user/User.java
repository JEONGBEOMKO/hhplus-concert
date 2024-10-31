package com.hhplus.concert.domain.user;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    private Long amount;  // 잔액

    @Version
    private Integer version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
    }

    // 잔액 증가 메서드
    public void addAmount(Long amount) {
        this.amount += amount;
    }

    // 잔액 감소 메서드
    public void deductAmount(Long amount) {
        if (this.amount < amount) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        this.amount -= amount;
    }
}
