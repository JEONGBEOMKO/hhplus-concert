package com.hhplus.concert.application.dto.output;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private UUID userId;
    private String name;
    private Long balance;
}
