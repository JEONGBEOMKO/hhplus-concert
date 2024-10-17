package com.hhplus.concert.domain.user;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID userId;  // UUID로 수정

    @Column(nullable = false)
    private String name;

    private Long amount;  // 잔액

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
    }

    public User() {
    }

    public User(UUID userId, String name, Long amount){
        this.userId = userId;
        this.name = name;
        this.amount = amount;
    }
}
