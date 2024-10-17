package com.hhplus.concert.interfaces.api.user;

import com.hhplus.concert.application.dto.request.ChargeRequest;
import com.hhplus.concert.application.dto.response.ChargeResponse;
import com.hhplus.concert.application.dto.response.UserResponse;
import com.hhplus.concert.application.usecase.ChargeBalanceUseCase;
import com.hhplus.concert.application.usecase.GetUserBalanceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final ChargeBalanceUseCase chargeBalanceUseCase;
    private final GetUserBalanceUseCase getUserBalanceUseCase;

    public UserController(ChargeBalanceUseCase chargeBalanceUseCase, GetUserBalanceUseCase getUserBalanceUseCase) {
        this.chargeBalanceUseCase = chargeBalanceUseCase;
        this.getUserBalanceUseCase = getUserBalanceUseCase;
    }

    // 잔액 충전 API
    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> chargeBalance(@RequestBody ChargeRequest request) {
        ChargeResponse response = chargeBalanceUseCase.chargeBalance(request.getUserId(), request.getAmount());
        return ResponseEntity.ok(response);
    }

    // 잔액 조회 API
    @GetMapping("/{userId}/balance")
    public ResponseEntity<UserResponse> getUserBalance(@PathVariable UUID userId) {
        Long balance = getUserBalanceUseCase.getBalance(userId); // 잔액 조회
        return ResponseEntity.ok(new UserResponse(userId, balance)); // 사용자 응답 DTO
    }
}
