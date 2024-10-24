package com.hhplus.concert.unit;

import com.hhplus.concert.application.dto.input.ConcertScheduleInput;
import com.hhplus.concert.application.dto.input.SeatInput;
import com.hhplus.concert.application.dto.output.ConcertScheduleOutput;
import com.hhplus.concert.application.dto.output.SeatOutput;
import com.hhplus.concert.application.usecase.GetAvailableDatesUseCase;
import com.hhplus.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.concert.domain.concertschedule.ConcertSchedule;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.ConcertScheduleRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationAvailabilityTest {
    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private GetAvailableDatesUseCase getAvailableDatesUseCase;

    @InjectMocks
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    private LocalDate date;
    private Long concertScheduleId;

    @BeforeEach
    public void setUp() {
        date = LocalDate.of(2024, 10, 25);
        concertScheduleId = 1L;
    }

    // 예약 가능 날짜 조회 성공 테스트
    @Test
    @DisplayName("예약 가능한 날짜 조회 성공 테스트")
    public void testGetAvailableDates_Success() {
        // Given
        LocalDate date = LocalDate.of(2024, 10, 25);
        ConcertSchedule schedule1 = new ConcertSchedule(1L, date, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50, 50, "AVAILABLE");
        ConcertSchedule schedule2 = new ConcertSchedule(1L, date, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50, 25, "AVAILABLE");

        when(concertScheduleRepository.findAllByConcertIdAndOpenAt(1L, date)).thenReturn(Arrays.asList(schedule1, schedule2));

        // When
        ConcertScheduleInput request = new ConcertScheduleInput(1L, date);
        List<ConcertScheduleOutput> availableDates = getAvailableDatesUseCase.getAvailableDates(request);

        // Then
        assertEquals(2, availableDates.size(), "예약 가능한 날짜의 개수가 예상과 다릅니다.");
        assertEquals("AVAILABLE", availableDates.get(0).getTotalSeatStatus(), "첫 번째 스케줄의 좌석 상태가 올바르지 않습니다.");
        assertEquals(date, availableDates.get(0).getOpenAt(), "날짜가 예상과 다릅니다.");
    }

    @Test
    @DisplayName("예약 가능 날짜 조회 실패 테스트")
    public void testGetAvailableDates_InvalidDate() {
        // Given
        ConcertScheduleInput request = new ConcertScheduleInput(1L, date);

        // When & Then
        assertThrows(java.time.format.DateTimeParseException.class, () -> {
            getAvailableDatesUseCase.getAvailableDates(request);
        }, "잘못된 날짜 형식을 입력하면 DateTimeParseException이 발생해야 합니다.");
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 성공 테스트")
    public void testGetAvailableSeats_Success() {
        // Given
        Seat seat1 = new Seat(concertScheduleId, 1000L, 1, "AVAILABLE");
        Seat seat2 = new Seat(concertScheduleId, 1000L, 2, "AVAILABLE");

        when(seatRepository.findByConcertScheduleId(concertScheduleId)).thenReturn(Arrays.asList(seat1, seat2));

        // When
        SeatInput request = new SeatInput(concertScheduleId);
        List<SeatOutput> availableSeats = getAvailableSeatsUseCase.getAvailableSeats(concertScheduleId);

        // Then
        assertEquals(2, availableSeats.size(), "예약 가능한 좌석의 개수는 2개여야 합니다.");
        assertEquals("AVAILABLE", availableSeats.get(0).getSeatStatus(), "첫 번째 좌석 상태는 AVAILABLE이어야 합니다.");
        assertTrue(availableSeats.get(0).getPosition() >= 1 && availableSeats.get(0).getPosition() <= 50, "좌석 번호는 1~50 사이여야 합니다.");
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 실패 테스트 (잘못된 좌석 번호 범위)")
    public void testGetAvailableSeats_InvalidPosition() {
        // Given
        Long concertScheduleId = 1L;
        Integer invalidPosition = 51; // 잘못된 좌석 번호 (1~50 범위 외)

        Seat invalidSeat = new Seat(concertScheduleId, 1000L, invalidPosition, "AVAILABLE");

        when(seatRepository.findByConcertScheduleId(concertScheduleId)).thenReturn(Arrays.asList(invalidSeat));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            getAvailableSeatsUseCase.getAvailableSeats(concertScheduleId);
        });
        assertEquals("좌석 번호는 1부터 50 사이여야 합니다.", exception.getMessage(), "잘못된 좌석 번호로 인해 IllegalArgumentException이 발생해야 합니다.");
    }

}
