package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID userId;
    private Long balance;

    public UserResponse(UUID userId, Long balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
