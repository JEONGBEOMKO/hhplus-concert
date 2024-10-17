package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChargeRequest {
    private UUID userId;
    private Long amount;

    public ChargeRequest(UUID userId, Long amount){
        this.userId = userId;
        this.amount = amount;
    }
}
