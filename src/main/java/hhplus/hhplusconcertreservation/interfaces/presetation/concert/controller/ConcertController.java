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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "CONCERT")
@RequestMapping("/concerts")
public class ConcertController {
	private final ConcertService concertService;

	@Operation(summary = "예약 결제")
	@PostMapping("/schedules/seats/bookings/{bookingId}/pay")
	public ConcertPaymentResponse pay(HttpServletRequest request, @PathVariable Long bookingId) {
		Long userId = (Long) request.getAttribute("userId");
		return new ConcertPaymentResponse(concertService.pay(userId, bookingId));
	}

	@Operation(summary = "좌석 예약")
	@PostMapping("/waiting/schedules/seats/{seatId}/book")
	public ConcertBookingResponse bookConcertSeat(HttpServletRequest request, @PathVariable Long seatId) {
		Long userId = (Long) request.getAttribute("userId");
		return new ConcertBookingResponse(concertService.bookConcertSeat(userId, seatId));
	}

	@Operation(summary = "공연 일정 조회")
	@GetMapping("/waiting/{concertId}/schedules")
	public List<ConcertScheduleResponse> concertSchedules(HttpServletRequest request, @PathVariable Long concertId) {
		Long userId = (Long) request.getAttribute("userId");
		return concertService.scanAllBookableConcertSchedules(userId, concertId)
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
	public List<ConcertSeatResponse> concertScheduleSeats(HttpServletRequest request, @PathVariable Long scheduleId) {
		Long userId = (Long) request.getAttribute("userId");
		return concertService.scanAllSeats(userId, scheduleId)
			.stream().map(ConcertSeatResponse::new).toList();
	}
}
