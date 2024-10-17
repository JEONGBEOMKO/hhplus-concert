package com.hhplus.concert.interfaces.api.payment;

import com.hhplus.concert.application.dto.response.PaymentResponse;
import com.hhplus.concert.domain.payment.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    // 결제 정보 조회 API


    //결제 처리 API
    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody Payment payment){
        return ResponseEntity.ok("결제가 처리되었습니다.");
    }
}
