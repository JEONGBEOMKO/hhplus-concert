package com.hhplus.concert.unit;

import com.hhplus.concert.application.usecase.PaymentProcessingUseCase;
import com.hhplus.concert.application.usecase.QueueProcessingScheduler;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.PaymentRepository;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
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
public class PaymentProcessingUseCaseTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private PaymentProcessingUseCase paymentProcessingUseCase;
    private QueueProcessingScheduler queueProcessingScheduler;

    private Queue expiredQueue1;
    private Queue expiredQueue2;

    @BeforeEach
    public void setUp() {
        expiredQueue1 = new Queue(UUID.randomUUID(), "token1", "WAITING", LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(5), 1);
        expiredQueue2 = new Queue(UUID.randomUUID(), "token2", "WAITING", LocalDateTime.now().minusMinutes(15), LocalDateTime.now().minusMinutes(5), 2);
    }

    @Test
    public void testProcessPayment_Success() {
        // Given
        UUID userId = UUID.randomUUID();
        Long seatId = 1L;
        Long concertScheduleId = 1L;
        Integer position = 1;
        Seat seat = new Seat(concertScheduleId, 1000L, position, "AVAILABLE");

        // seatRepository가 seatId로 조회할 때 Optional.of(seat)를 반환하도록 설정
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // 대기열 Queue 설정
        Queue activeQueue = new Queue(userId, "token", "ACTIVE", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), 1);
        when(queueRepository.findByUserIdAndStatus(userId, "ACTIVE")).thenReturn(Optional.of(activeQueue));

        // When
        paymentProcessingUseCase.processPayment(userId, seatId);

        // Then
        verify(paymentRepository).save(any(Payment.class)); // 결제 내역 저장 확인
        assertEquals("OCCUPIED", seat.getSeatStatus()); // 좌석 상태 확인
        assertEquals("EXPIRED", activeQueue.getStatus()); // 대기열 상태 확인
    }

    @Test
    public void testProcessPayment_SeatNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        Long seatId = 1L;

        // lenient()를 사용하여 불필요한 stubbing 예외 방지
        lenient().when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentProcessingUseCase.processPayment(userId, seatId);
        });

        assertEquals("좌석을 찾을 수 없습니다.", exception.getMessage()); // 예외 메시지 검증
    }

    @Test
    public void testProcessPayment_ActiveQueueNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        Long seatId = 1L;
        Integer position = 1; // Integer 타입의 position 필드 사용
        Seat seat = new Seat(seatId, 1000L, position, "AVAILABLE");

        // Seat는 존재하지만 Queue는 찾지 못하도록 설정
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(queueRepository.findByUserIdAndStatus(userId, "ACTIVE")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentProcessingUseCase.processPayment(userId, seatId);
        });

        assertEquals("대기열 항목을 찾을 수 없습니다.", exception.getMessage()); // 예외 메시지 검증
    }

    @Test
    public void testProcessExpiredQueues() {
        // Given
        List<Queue> expiredQueues = Arrays.asList(expiredQueue1, expiredQueue2);
        when(queueRepository.findAllByExpiredAtBeforeAndStatus(any(LocalDateTime.class), eq("WAITING"))).thenReturn(expiredQueues);

        // When
        queueProcessingScheduler.processExpiredQueues();

        // Then
        assertEquals("EXPIRED", expiredQueue1.getStatus());
        assertEquals("EXPIRED", expiredQueue2.getStatus());
        verify(queueRepository, times(1)).save(expiredQueue1);
        verify(queueRepository, times(1)).save(expiredQueue2);
    }

}
