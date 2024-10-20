package com.hhplus.concert.application.dto.input;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {
    private UUID userId;
    private String name;
}
