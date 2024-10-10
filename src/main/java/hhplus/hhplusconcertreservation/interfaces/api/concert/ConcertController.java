package hhplus.hhplusconcertreservation.interfaces.api.concert;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.interfaces.api.common.dto.CreateResponse;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.request.ReservationRequest;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.request.ReservationTokenRequest;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response.ConcertResponse;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response.ConcertSeatResponse;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response.ReservationTokenResponse;
import hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response.ReservationWaitingTurnResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

	@GetMapping
	public ResponseEntity<List<ConcertResponse>> concerts() {
		List<ConcertResponse> response = List.of(
			ConcertResponse.builder()
				.concertId(1L)
				.concertDateId(1L)
				.performanceDate(LocalDateTime.now().plusDays(1))
				.title("첫번째 공연")
				.place("예술의전당 콘서트홀")
				.build(),
			ConcertResponse.builder()
				.concertId(2L)
				.concertDateId(2L)
				.performanceDate(LocalDateTime.now().plusDays(2))
				.title("두번째 공연")
				.place("예술의전당 콘서트홀")
				.build()
		);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{concertId}/seats")
	public ResponseEntity<List<ConcertSeatResponse>> concertSeats(
		@PathVariable Long concertId,
		@RequestParam Integer concertDateId
	) {
		List<ConcertSeatResponse> response = List.of(
			ConcertSeatResponse.builder()
				.seatId(1L)
				.seatNumber(1L)
				.price(1000L)
				.isReserved(false)
				.build(),
			ConcertSeatResponse.builder()
				.seatId(2L)
				.seatNumber(2L)
				.price(2000L)
				.isReserved(true)
				.build(),
			ConcertSeatResponse.builder()
				.seatId(3L)
				.seatNumber(3L)
				.price(3000L)
				.isReserved(true)
				.build()
		);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/reservation/waiting-turn")
	public ResponseEntity<ReservationWaitingTurnResponse> reservationTurn() {
		return new ResponseEntity<>(ReservationWaitingTurnResponse.builder().turn(1L).isAvailable(false).build(), HttpStatus.OK);
	}

	@PostMapping("/reservation")
	public ResponseEntity<CreateResponse> reserve(
		@RequestBody ReservationRequest request
	) {
		CreateResponse response = CreateResponse.builder()
			.id(1L)
			.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/reservation/token")
	public ResponseEntity<ReservationTokenResponse> token(
		@RequestBody ReservationTokenRequest request
	) {
		return new ResponseEntity<>(ReservationTokenResponse.builder().token("abcde").build(), HttpStatus.OK);
	}
}
