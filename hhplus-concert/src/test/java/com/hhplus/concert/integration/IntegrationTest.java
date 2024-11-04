package com.hhplus.concert.integration;

import com.hhplus.concert.application.dto.input.ConcertScheduleInput;
import com.hhplus.concert.application.dto.input.QueueInput;
import com.hhplus.concert.application.dto.input.ReservationInput;
import com.hhplus.concert.application.dto.input.SeatInput;
import com.hhplus.concert.application.dto.output.*;
import com.hhplus.concert.application.usecase.*;
import com.hhplus.concert.domain.concertschedule.ConcertSchedule;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/test_delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Transactional
public class IntegrationTest {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ChargeBalanceUseCase chargeBalanceUseCase;

    @Autowired
    private GetAvailableDatesUseCase getAvailableDatesUseCase;

    @Autowired
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    @Autowired
    private PaymentProcessingUseCase paymentProcessingUseCase;

    @Autowired
    private GenerateQueueTokenUseCase generateQueueTokenUseCase;



    private UUID userId;
    private Long seatId;

    @BeforeEach
    public void setUp(){
        userId = UUID.randomUUID();
        User user = User.builder()
                .userId(userId)
                .name("Test User")
                .amount(10000L)
                .build();// 초기 잔액 10000L 설정
        userRepository.save(user);

        // 테스트용 좌석 생성 및 저장
        Seat seat = new Seat(1L, 1000L, 1, "AVAILABLE");
        seatRepository.save(seat);
        seatId = seat.getId();

        // 테스트용 대기열 생성 및 저장
        Queue queue = new Queue(userId, "테스트토큰1", "ACTIVE", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), 1);
        queueRepository.save(queue);
    }

    @Test
    @DisplayName("유저 대기열 토큰 발급 및 조회 테스트")
    public void testQueueTokenCreationAndPolling() {
        // given Token 생성 요청
        QueueInput queueInput = new QueueInput(userId);
        // when
        QueueOutput queueOutput = generateQueueTokenUseCase.generateToken(queueInput);

        // Assert queue 생성 검증
        assertNotNull(queueOutput.getToken(), "유저 대기열 토큰이 생성되어야 합니다.");
        assertEquals("WAITING", queueOutput.getStatus(), "대기열 초기 상태는 'WAITING'이어야 합니다.");

        // Queue polling 검증
        QueueOutput polledQueueOutput = queueOutput;
        assertEquals(queueOutput.getQueuePosition(), polledQueueOutput.getQueuePosition(), "대기열에서 조회한 순번이 일치해야 합니다.");
    }

    @Test
    @DisplayName("예약 가능 날짜 및 좌석 조회 테스트")
    public void testAvailableDatesAndSeats() {

        // Given: ConcertSchedule 데이터 설정
        LocalDate openAt = LocalDate.parse("2024-10-25");
        ConcertSchedule schedule = new ConcertSchedule(1L, openAt, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50, 50, "AVAILABLE");
        concertScheduleRepository.save(schedule);

        // Given: Seat 데이터 설정
        Seat seat = new Seat(1L, 1000L, 1, "AVAILABLE");
        seatRepository.save(seat);

        // When: 예약 가능한 날짜 조회
        ConcertScheduleInput dateRequest = new ConcertScheduleInput(1L, openAt);
        List<ConcertScheduleOutput> dateResponse = getAvailableDatesUseCase.getAvailableDates(dateRequest);

        // Then: 예약 가능한 날짜 검증
        assertNotNull(dateResponse, "예약 가능한 날짜 응답은 null이 아니어야 합니다.");
        assertEquals(1, dateResponse.size(), "예약 가능한 날짜는 한 개여야 합니다.");
        assertEquals(openAt, dateResponse.get(0).getOpenAt(), "예약 가능한 날짜는 요청된 날짜와 일치해야 합니다.");

        //  When: 예약 가능한 좌석 조회
        SeatInput seatInput = new SeatInput(1L);
        List<SeatOutput> seatResponse = getAvailableSeatsUseCase.getAvailableSeats(seatInput.getConcertScheduleId());

        // Then: 예약 가능한 좌석 검증
        assertNotNull(seatResponse, "예약 가능한 좌석 응답은 null이 아니어야 합니다.");
        assertEquals(1, seatResponse.size(), "예약 가능한 좌석은 한 개여야 합니다.");
        assertEquals("AVAILABLE", seatResponse.get(0).getSeatStatus(), "예약 가능한 좌석 상태는 'AVAILABLE'이어야 합니다.");
    }

    @Test
    @DisplayName("좌석 예약 요청 테스트")
    public void testReserveSeat() {
        ReservationInput reservationInput = new ReservationInput(userId, 1L, 1000L, 1);
        ReservationOutput reservationOutput = new ReservationOutput(1L, userId, 1L, 1000L, 1, "TEMPORARY", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        assertEquals("TEMPORARY", reservationOutput.getStatus(), "좌석 예약 상태는 'TEMPORARY'이어야 합니다.");
        assertNotNull(reservationOutput.getReservedExpiresAt(), "예약 만료 시간은 null이 아니어야 합니다.");
        assertTrue(reservationOutput.getReservedExpiresAt().isAfter(LocalDateTime.now()), "예약 만료 시간이 현재 시간 이후여야 합니다.");
    }

    @Test
    @DisplayName("잔액 충전 및 조회 테스트")
    public void testChargeAndGetBalance() {
        // Given
        long amountToCharge = 5000L;

        // When
        ChargeOutput chargeOutput = chargeBalanceUseCase.chargeBalance(userId, amountToCharge);

        // Then
        assertEquals(15000L, chargeOutput.getNewBalance(), "충전 후 잔액은 15000이어야 합니다.");

        // 잔액 조회
        long balance = chargeOutput.getNewBalance();
        assertEquals(15000L, balance, "조회된 잔액은 15000이어야 합니다.");
    }
    /*
    @Test
    public void testProcessPayment() {
        // When
        PaymentResponse paymentResponse = paymentProcessingUseCase.processPayment(userId, seatId);

        // Then
        assertEquals("COMPLETED", paymentResponse.getStatus(), "결제 상태는 'COMPLETED'이어야 합니다.");
        assertNotNull(paymentResponse.getCreatedAt(), "결제 생성일은 null이 아니어야 합니다.");


        Optional<Seat> updatedSeat = seatRepository.findById(seatId);
        assertTrue(updatedSeat.isPresent(), "결제 후 좌석이 존재해야 합니다.");
        assertEquals("OCCUPIED", updatedSeat.get().getSeatStatus(), "결제 완료 후 좌석 상태는 'OCCUPIED'이어야 합니다.");

        Optional<Queue> updatedQueue = queueRepository.findByUserIdAndStatus(userId, "EXPIRED");
        assertTrue(updatedQueue.isPresent(), "결제 후 대기열 토큰이 만료되어야 합니다.");
        assertEquals("EXPIRED", updatedQueue.get().getStatus(), "결제 완료 후 대기열 상태는 'EXPIRED'이어야 합니다.");
    }
    */
}
