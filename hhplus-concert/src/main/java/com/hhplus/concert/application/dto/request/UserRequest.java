package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private UUID userId;

    public UserRequest(UUID userId){
        this.userId = userId;
    }
}
