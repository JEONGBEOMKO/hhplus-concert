package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.input.PaymentInput;
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


    public PaymentProcessingUseCase(PaymentRepository paymentRepository, SeatRepository seatRepository, QueueRepository queueRepository) {
        this.paymentRepository = paymentRepository;
        this.seatRepository = seatRepository;
        this.queueRepository = queueRepository;
    }

    @Transactional
    public PaymentOutput processPayment(PaymentInput paymentInput) {

        Seat seat = seatRepository.findById(paymentInput.getSeatId())
                .orElseThrow(() -> new NoSuchElementException("좌석을 찾을 수 없습니다."));

        if ("TEMPORARY".equals(seat.getSeatStatus())) {
            throw new IllegalStateException("좌석은 이미 예약된 상태입니다.");
        }

        long price = seat.getAmount();

        // 결제 정보 생성
        Payment payment = new Payment(paymentInput.getUserId(), paymentInput.getSeatId(), price, "COMPLETED");

        Payment savedPayment = paymentRepository.save(payment);

        seat.setSeatStatus("OCCUPIED");
        seatRepository.save(seat);

        // 대기열 토큰 만료
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
