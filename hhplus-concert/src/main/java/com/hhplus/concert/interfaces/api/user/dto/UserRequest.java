package com.hhplus.concert.interfaces.api.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private UUID userId;
    private String name;
    private Long amount;
}
