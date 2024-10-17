package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.request.ConcertScheduleRequest;
import com.hhplus.concert.application.dto.response.ConcertScheduleResponse;
import com.hhplus.concert.domain.concertschedule.ConcertSchedule;
import com.hhplus.concert.infrastructure.repository.ConcertScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAvailableDatesUseCase {
    private final ConcertScheduleRepository concertScheduleRepository;


    public GetAvailableDatesUseCase(ConcertScheduleRepository concertScheduleRepository) {
        this.concertScheduleRepository = concertScheduleRepository;
    }

    public List<ConcertScheduleResponse> getAvailableDates(ConcertScheduleRequest request) {
        List<ConcertSchedule> schedules = concertScheduleRepository.findAllByOpenAt(LocalDate.parse(request.getDate()));
        return schedules.stream()
                .map(schedule -> new ConcertScheduleResponse(
                        schedule.getId(),
                        schedule.getConcertId(),
                        schedule.getOpenAt(),
                        schedule.getStartAt(),
                        schedule.getEndAt(),
                        schedule.getTotalSeat(),
                        schedule.getAvailableSeat(),
                        schedule.getTotalSeatStatus()
                ))
                .collect(Collectors.toList());
    }
}
