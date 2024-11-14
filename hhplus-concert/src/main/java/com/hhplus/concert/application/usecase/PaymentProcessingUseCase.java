package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.input.PaymentInput;
import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.application.dto.output.PaymentOutput;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.PaymentRepository;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentProcessingUseCase {
    private final PaymentRepository paymentRepository;
    private final SeatRepository seatRepository;
    private final QueueRepository queueRepository;
    private final DeductBalanceUseCase deductBalanceUseCase;


    public PaymentProcessingUseCase(PaymentRepository paymentRepository, SeatRepository seatRepository, QueueRepository queueRepository, DeductBalanceUseCase deductBalanceUseCase) {
        this.paymentRepository = paymentRepository;
        this.seatRepository = seatRepository;
        this.queueRepository = queueRepository;
        this.deductBalanceUseCase = deductBalanceUseCase;
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


        // 3. 결제 정보 생성 및 저장
        Payment payment = new Payment(paymentInput.getUserId(), paymentInput.getSeatId(), seat.getAmount(), "COMPLETED");
        Payment savedPayment = paymentRepository.save(payment);

        // 4. 좌석 상태 변경
        seat.setSeatStatus("OCCUPIED");
        seatRepository.save(seat);

        // 5. 대기열 토큰 만료
        Queue queue = queueRepository.findByUserIdAndStatus(payment.getUserId(), "ACTIVE")
                .orElseThrow(() -> new NoSuchElementException("대기열 항목을 찾을 수 없습니다."));
        queue.setStatus("EXPIRED");
        queueRepository.save(queue);

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
