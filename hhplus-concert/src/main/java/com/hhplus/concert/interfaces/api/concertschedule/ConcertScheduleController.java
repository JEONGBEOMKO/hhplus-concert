package com.hhplus.concert.interfaces.api.concertschedule;

import com.hhplus.concert.application.dto.input.ConcertScheduleInput;
import com.hhplus.concert.application.dto.output.ConcertScheduleOutput;
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
    public ResponseEntity<List<ConcertScheduleOutput>> getAvailableDates(@RequestBody ConcertScheduleInput request) {
        List<ConcertScheduleOutput> response = getAvailableDatesUseCase.getAvailableDates(request);
        return ResponseEntity.ok(response);
    }
}
