package hhplus.hhplusconcertreservation.interfaces.presetation.concert.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.concert.service.ConcertService;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertBookingResponse;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertScheduleResponse;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertSeatsResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {
	private final ConcertService concertService;

	@PostMapping("/schedules/seats/{seatId}/book")
	public ConcertBookingResponse bookConcertSeat(@RequestHeader("WaitingToken") String token, @PathVariable Long seatId) {
		String jwtToken = token.replace("Bearer ", "");
		return new ConcertBookingResponse(concertService.bookConcertSeat(jwtToken, seatId));
	}

	@GetMapping("/{concertId}/schedules")
	public List<ConcertScheduleResponse> concertSchedules(@RequestHeader("WaitingToken") String token, @PathVariable Long concertId) {
		String jwtToken = token.replace("Bearer ", "");

		return concertService.scanAllBookableConcertSchedules(jwtToken, concertId)
			.stream().map(concertSchedule -> new ConcertScheduleResponse(
				concertSchedule.getId(),
				concertSchedule.getConcert(),
				concertSchedule.getBookingStartDate(),
				concertSchedule.getBookingEndDate(),
				concertSchedule.getPerformanceDate())
			).toList();
	}

	@GetMapping("/schedules/{scheduleId}/seats")
	public List<ConcertSeatsResponse> concertScheduleSeats(@RequestHeader("WaitingToken") String token, @PathVariable Long scheduleId) {
		String jwtToken = token.replace("Bearer ", "");

		return concertService.scanAllSeats(jwtToken, scheduleId)
			.stream().map(ConcertSeatsResponse::new).toList();
	}
}
