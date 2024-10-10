package com.hhplus.concert.interfaces.api.balance;

import com.hhplus.concert.interfaces.api.balance.dto.Balance;
import com.hhplus.concert.interfaces.api.balance.dto.ChargeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    // 잔액 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<Balance> getBalance(@PathVariable Long userId){
        Balance balance = new Balance(userId, new BigDecimal("10000"));
        return ResponseEntity.ok(balance);
    }

    // 잔액 충전 API
    @PostMapping("/charge")
    public ResponseEntity<String> chargeBalance(@RequestBody ChargeRequest chargeRequest) {
        return ResponseEntity.ok("충전이 완료되었습니다.");
    }
}
