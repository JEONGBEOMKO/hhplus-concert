package com.hhplus.concert.interfaces.api.concertschedule;

import com.hhplus.concert.application.dto.input.ConcertScheduleInput;
import com.hhplus.concert.application.dto.output.ConcertScheduleOutput;
import com.hhplus.concert.application.usecase.GetAvailableDatesUseCase;
import com.hhplus.concert.interfaces.api.concertschedule.dto.ConcertScheduleRequest;
import com.hhplus.concert.interfaces.api.concertschedule.dto.ConcertScheduleResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/concert-schedules")
public class ConcertScheduleController {
    private final GetAvailableDatesUseCase getAvailableDatesUseCase;

    public ConcertScheduleController(GetAvailableDatesUseCase getAvailableDatesUseCase) {
        this.getAvailableDatesUseCase = getAvailableDatesUseCase;
    }

    // 예약 가능 날짜 조회 API
    @Cacheable(value = "availableDates", key = "#request.concertId + '_' + #request.openAt")
    @GetMapping("/available-dates")
    public ResponseEntity<List<ConcertScheduleResponse>> getAvailableDates(@RequestBody ConcertScheduleRequest request) {
        ConcertScheduleInput input = new ConcertScheduleInput(
                request.getConcertId(),
                request.getOpenAt()
        );

        List<ConcertScheduleOutput> scheduleOutputs = getAvailableDatesUseCase.getAvailableDates(input);

        List<ConcertScheduleResponse> response = scheduleOutputs.stream()
                .map(scheduleOutput -> new ConcertScheduleResponse(
                        scheduleOutput.getConcertScheduleId(),
                        scheduleOutput.getConcertId(),
                        scheduleOutput.getOpenAt(),
                        scheduleOutput.getStartAt(),
                        scheduleOutput.getEndAt(),
                        scheduleOutput.getTotalSeat(),
                        scheduleOutput.getAvailableSeat(),
                        scheduleOutput.getTotalSeatStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
