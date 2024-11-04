package com.hhplus.concert.integration;

import com.hhplus.concert.application.dto.input.ReservationInput;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.ReservationRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "test_delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class ReserveSeatConcurrencyTest {
    @Autowired
    private ReserveSeatUseCase reserveSeatUseCase;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Long seatId;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        // given
        Seat seat = new Seat();
        seat.setSeatStatus("AVAILABLE");
        seat.setAmount(1000L);
        seat = seatRepository.save(seat);
        seatId = seat.getId();

        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("동시 좌석 예약 시 하나의 요청만 성공해야 한다.")
    public void testConcurrentSeatReservation() throws InterruptedException {
        // given
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ReservationInput input = new ReservationInput(userId, seatId, 1000L, 1);
                    reserveSeatUseCase.reserveSeat(input);
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        long reservationCount = reservationRepository.countBySeatIdAndStatus(seatId, "TEMPORARY");
        assertEquals(1, reservationCount, "동시 예약 시 하나의 요청만 성공해야 합니다.");

        // then
        Seat reservedSeat = seatRepository.findById(seatId).orElseThrow();
        assertEquals("TEMPORARY", reservedSeat.getSeatStatus(), "좌석 상태는 'TEMPORARY'이어야 합니다.");
    }
}
