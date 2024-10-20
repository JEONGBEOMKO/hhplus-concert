package com.hhplus.concert.interfaces.api.user;

import com.hhplus.concert.application.dto.input.ChargeInput;
import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.application.dto.output.UserOutput;
import com.hhplus.concert.application.usecase.ChargeBalanceUseCase;
import com.hhplus.concert.application.usecase.GetUserBalanceUseCase;
import com.hhplus.concert.domain.user.User;
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
    public ResponseEntity<ChargeOutput> chargeBalance(@RequestBody ChargeInput request) {
        ChargeOutput response = chargeBalanceUseCase.chargeBalance(request.getUserId(), request.getAmount());
        return ResponseEntity.ok(response);
    }

    // 잔액 조회 API
    @GetMapping("/{userId}/balance")
    public ResponseEntity<UserOutput> getUserBalance(@PathVariable UUID userId) {
        User user = getUserBalanceUseCase.getUserById(userId);
        Long balance = getUserBalanceUseCase.getBalance(userId);
        return ResponseEntity.ok(new UserOutput(user.getUserId(), user.getName(), balance));
    }
}
