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
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertPaymentResponse;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertScheduleResponse;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertSeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "CONCERT")
@RequestMapping("/concerts")
public class ConcertController {
	private final ConcertService concertService;

	@Operation(summary = "예약 결제")
	@PostMapping("/schedules/seats/bookings/{bookingId}/pay")
	public ConcertPaymentResponse pay(@RequestHeader("Authorization") String token, @PathVariable Long bookingId) {
		String jwtToken = token.replace("Bearer ", "");
		return new ConcertPaymentResponse(concertService.pay(jwtToken, bookingId));
	}

	@Operation(summary = "좌석 예약")
	@PostMapping("/waiting/schedules/seats/{seatId}/book")
	public ConcertBookingResponse bookConcertSeat(@RequestHeader("WaitingToken") String token, @PathVariable Long seatId) {
		String jwtToken = token.replace("Bearer ", "");
		return new ConcertBookingResponse(concertService.bookConcertSeat(jwtToken, seatId));
	}

	@Operation(summary = "공연 일정 조회")
	@GetMapping("/waiting/{concertId}/schedules")
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

	@Operation(summary = "공연 좌석 조회")
	@GetMapping("/waiting/schedules/{scheduleId}/seats")
	public List<ConcertSeatResponse> concertScheduleSeats(@RequestHeader("WaitingToken") String token, @PathVariable Long scheduleId) {
		String jwtToken = token.replace("Bearer ", "");

		return concertService.scanAllSeats(jwtToken, scheduleId)
			.stream().map(ConcertSeatResponse::new).toList();
	}
}
