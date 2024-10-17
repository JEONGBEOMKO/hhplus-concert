package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChargeResponse {
    private UUID userID;
    private Long newBalance;

    public ChargeResponse(UUID userID, Long newBalance){
        this.userID = userID;
        this.newBalance = newBalance;
    }
}
