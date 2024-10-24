package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.PaymentOutput;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.infrastructure.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetUserPaymentHistoryUseCase {
    private final PaymentRepository paymentRepository;

    public GetUserPaymentHistoryUseCase(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentOutput> getUserPaymentHistory(UUID userId){
        List<Payment> payments = paymentRepository.findByUserId(userId);

        return payments.stream()
                .map(payment -> new PaymentOutput(
                        payment.getId(),
                        payment.getUserId(),
                        payment.getSeatId(),
                        payment.getPrice(),
                        payment.getStatus(),
                        payment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
