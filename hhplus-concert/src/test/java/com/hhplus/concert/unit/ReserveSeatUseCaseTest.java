package com.hhplus.concert.unit;

import com.hhplus.concert.application.dto.request.ReservationRequest;
import com.hhplus.concert.application.dto.response.ReservationResponse;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import com.hhplus.concert.domain.reservation.Reservation;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.ReservationRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReserveSeatUseCaseTest {
    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReserveSeatUseCase reserveSeatUseCase;

    private UUID userId;
    private Long seatId;
    private Seat seat;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        seatId = 1L;
        seat = new Seat(1L, 1000L, 1, "AVAILABLE");
    }

    // 성공 테스트: 좌석이 임시로 사용자에게 배정되고, 예약 정보가 저장되는 경우
    @Test
    public void testReserveSeat_Success() {
        // Given
        ReservationRequest request = new ReservationRequest(userId, seatId, 1000L, 1);
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        LocalDateTime expectedExpirationTime = LocalDateTime.now().plusMinutes(5);
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> {
                    Reservation reservation = invocation.getArgument(0);
                    reservation.setReservedExpiresAt(expectedExpirationTime);
                    return reservation;
                });

        // When
        ReservationResponse response = reserveSeatUseCase.reserveSeat(request);

        // Then
        assertEquals("TEMPORARY", seat.getSeatStatus(), "좌석 상태가 임시 예약(TEMPORARY)이어야 합니다.");
        assertNotNull(response, "예약 응답은 null이 아니어야 합니다.");
        assertEquals("TEMPORARY", response.getStatus(), "예약 상태가 TEMPORARY이어야 합니다.");
        assertNotNull(response.getReservedExpiresAt(), "예약 만료 시간은 null이 아니어야 합니다.");
        assertTrue(response.getReservedExpiresAt().isAfter(LocalDateTime.now()), "예약 만료 시간이 현재 시간 이후여야 합니다.");
    }

    // 실패 테스트: 이미 예약된 좌석에 대해 요청할 때 예외가 발생해야 함
    @Test
    public void testReserveSeat_Fail_AlreadyReserved() {
        // Given
        seat.setSeatStatus("OCCUPIED"); // 좌석이 이미 예약된 상태
        ReservationRequest request = new ReservationRequest(userId, seatId, 1000L, 1);

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            reserveSeatUseCase.reserveSeat(request);
        });
        assertEquals("이미 예약된 좌석입니다.", exception.getMessage(), "이미 예약된 좌석에 대해 예외가 발생해야 합니다.");
    }

    // 실패 테스트: 존재하지 않는 좌석에 대해 요청할 때 예외가 발생해야 함
    @Test
    public void testReserveSeat_Fail_SeatNotFound() {
        // Given
        ReservationRequest request = new ReservationRequest(userId, seatId, 1000L, 1);

        when(seatRepository.findById(seatId)).thenReturn(Optional.empty()); // 좌석을 찾을 수 없음

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reserveSeatUseCase.reserveSeat(request);
        });
        assertEquals("좌석을 찾을 수 없습니다.", exception.getMessage(), "존재하지 않는 좌석에 대해 예외가 발생해야 합니다.");
    }
}
