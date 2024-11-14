package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.input.PaymentInput;
import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.application.dto.output.PaymentOutput;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.domain.payment.event.PaymentCompletedEvent;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.event.EventPublisher;
import com.hhplus.concert.infrastructure.repository.PaymentRepository;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PaymentProcessingUseCase {
    private final PaymentRepository paymentRepository;
    private final SeatRepository seatRepository;
    private final QueueRepository queueRepository;
    private final DeductBalanceUseCase deductBalanceUseCase;
    private final EventPublisher<PaymentCompletedEvent> paymentEventPublisher;


    public PaymentProcessingUseCase(PaymentRepository paymentRepository, SeatRepository seatRepository, QueueRepository queueRepository, DeductBalanceUseCase deductBalanceUseCase, EventPublisher<PaymentCompletedEvent> paymentEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.seatRepository = seatRepository;
        this.queueRepository = queueRepository;
        this.deductBalanceUseCase = deductBalanceUseCase;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Transactional
    public PaymentOutput processPayment(PaymentInput paymentInput) {

        // 1. 좌석 조회 및 상태 확인
        Seat seat = seatRepository.findById(paymentInput.getSeatId())
                .orElseThrow(() -> new NoSuchElementException("좌석을 찾을 수 없습니다."));

        if ("TEMPORARY".equals(seat.getSeatStatus())) {
            throw new IllegalStateException("좌석은 이미 예약된 상태입니다.");
        }

        // 2. 유저 조회 및 잔액 차감
        ChargeOutput chargeOutput = deductBalanceUseCase.deductBalance(paymentInput.getUserId(), seat.getAmount());

        // 3. 좌석 상태 변경
        seat.setSeatStatus("OCCUPIED");
        seatRepository.save(seat);

        // 4. 결제 정보 생성 및 저장
        Payment payment = new Payment(paymentInput.getUserId(), paymentInput.getSeatId(), seat.getAmount(), "COMPLETED");
        Payment savedPayment = paymentRepository.save(payment);

        // 5. 대기열 토큰 만료
        Queue queue = queueRepository.findByUserIdAndStatus(payment.getUserId(), "ACTIVE")
                .orElseThrow(() -> new NoSuchElementException("대기열 항목을 찾을 수 없습니다."));
        queue.setStatus("EXPIRED");
        queueRepository.save(queue);

        // 6. 결제 완료 이벤트 발행
        PaymentCompletedEvent event = PaymentCompletedEvent.builder()
                .paymentId(savedPayment.getId())
                .userId(paymentInput.getUserId())
                .build();

        paymentEventPublisher.publish(event);

        return new PaymentOutput(
                savedPayment.getId(),
                savedPayment.getUserId(),
                savedPayment.getSeatId(),
                savedPayment.getPrice(),
                savedPayment.getStatus(),
                savedPayment.getCreatedAt()
        );
    }
}
