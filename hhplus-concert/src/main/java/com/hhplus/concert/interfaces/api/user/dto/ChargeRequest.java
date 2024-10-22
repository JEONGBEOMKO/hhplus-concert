package com.hhplus.concert.interfaces.api.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {
    private UUID userId;
    private Long amount;
}
