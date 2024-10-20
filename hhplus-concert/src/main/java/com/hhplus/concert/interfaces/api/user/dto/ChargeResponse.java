package com.hhplus.concert.interfaces.api.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeResponse {
    private UUID userId;
    private Long newBalance;
}
