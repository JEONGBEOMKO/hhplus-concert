package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.response.PaymentResponse;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.PaymentRepository;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void processPayment(UUID userId, Long seatId) {
        // 좌석 정보 조회
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));

        long price = seat.getAmount(); // 실제 좌석 가격을 DB에서 가져옴

        // 결제 정보 생성
        Payment payment = new Payment(userId, seatId, price, "COMPLETED");

        // 결제 내역 저장
        Payment savedPayment = paymentRepository.save(payment);

        // 좌석의 소유권 부여
        seat.setSeatStatus("OCCUPIED"); // 소유권을 부여할 경우 상태 변경
        seatRepository.save(seat);

        // 대기열 토큰 만료
        Queue queue = queueRepository.findByUserIdAndStatus(userId, "ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("대기열 항목을 찾을 수 없습니다."));
        queue.setStatus("EXPIRED");
        queueRepository.save(queue);


    }

}
