package com.hhplus.concert.interfaces.api.concert;

import com.hhplus.concert.application.dto.request.ConcertScheduleRequest;
import com.hhplus.concert.application.dto.response.ConcertScheduleResponse;
import com.hhplus.concert.application.usecase.GetAvailableDatesUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concert-schedules")
public class ConcertScheduleController {
    private final GetAvailableDatesUseCase getAvailableDatesUseCase;

    public ConcertScheduleController(GetAvailableDatesUseCase getAvailableDatesUseCase) {
        this.getAvailableDatesUseCase = getAvailableDatesUseCase;
    }

    @GetMapping("/available-dates")
    public ResponseEntity<List<ConcertScheduleResponse>> getAvailableDates(@RequestBody ConcertScheduleRequest request) {
        List<ConcertScheduleResponse> response = getAvailableDatesUseCase.getAvailableDates(request);
        return ResponseEntity.ok(response);
    }
}
