package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponse {
    private Long reservationId;      // 예약 ID
    private UUID userId;             // 유저 ID
    private Long seatId;             // 좌석 ID
    private Long seatAmount;         // 좌석 금액
    private Integer seatPosition;     // 좌석 위치
    private String status;           // 예약 상태
    private LocalDateTime reservedAt;        // 예약 시각
    private LocalDateTime reservedExpiresAt; // 예약 만료 시각

    // 매개변수 생성자
    public ReservationResponse(Long reservationId, UUID userId, Long seatId, Long seatAmount, Integer seatPosition, String status, LocalDateTime reservedAt, LocalDateTime reservedExpiresAt) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.seatId = seatId;
        this.seatAmount = seatAmount;
        this.seatPosition = seatPosition;
        this.status = status;
        this.reservedAt = reservedAt;
        this.reservedExpiresAt = reservedExpiresAt;
    }
}
