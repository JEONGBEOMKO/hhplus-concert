package com.hhplus.concert.interfaces.api.payment;

import com.hhplus.concert.application.dto.input.PaymentInput;
import com.hhplus.concert.application.dto.output.PaymentOutput;
import com.hhplus.concert.application.usecase.GetUserPaymentHistoryUseCase;
import com.hhplus.concert.application.usecase.PaymentProcessingUseCase;
import com.hhplus.concert.domain.payment.Payment;
import com.hhplus.concert.interfaces.api.payment.dto.PaymentRequest;
import com.hhplus.concert.interfaces.api.payment.dto.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentProcessingUseCase paymentProcessingUseCase;
    private final GetUserPaymentHistoryUseCase getUserPaymentHistoryUseCase;

    public PaymentController(PaymentProcessingUseCase paymentProcessingUseCase, GetUserPaymentHistoryUseCase getUserPaymentHistoryUseCase) {
        this.paymentProcessingUseCase = paymentProcessingUseCase;
        this.getUserPaymentHistoryUseCase = getUserPaymentHistoryUseCase;
    }

    //결제 처리 API
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest){
        PaymentInput paymentInput = new PaymentInput(
                paymentRequest.getUserId(),
                paymentRequest.getSeatId(),
                paymentRequest.getAmount()
        );

        PaymentOutput paymentOutput = paymentProcessingUseCase.processPayment(paymentInput);

        PaymentResponse paymentResponse = new PaymentResponse(
                paymentOutput.getPaymentId(),
                paymentOutput.getUserId(),
                paymentOutput.getSeatId(),
                paymentOutput.getPrice(),
                paymentOutput.getStatus(),
                paymentOutput.getCreatedAt()
        );
        return ResponseEntity.ok(paymentResponse);
    }

    // 사용자의 결제 내역 조회 API
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<PaymentResponse>> getUserPaymentHistory(@PathVariable UUID userId) {
        // UseCase 호출하여 결제 내역 조회
        List<PaymentOutput> paymentOutputs = getUserPaymentHistoryUseCase.getUserPaymentHistory(userId);

        // Output -> Response 변환
        List<PaymentResponse> paymentResponses = paymentOutputs.stream()
                .map(paymentOutput -> new PaymentResponse(
                        paymentOutput.getPaymentId(),
                        paymentOutput.getUserId(),
                        paymentOutput.getSeatId(),
                        paymentOutput.getPrice(),
                        paymentOutput.getStatus(),
                        paymentOutput.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(paymentResponses);
    }
}
