package com.hhplus.concert.interfaces.api.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID userId;
    private String name;
    private Long balance;
}
