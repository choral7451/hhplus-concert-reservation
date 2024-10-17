package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import lombok.Getter;

@Getter
public class ConcertBookingResponse {
	private final Long id;
	private final Long concertId;
	private final Long concertScheduleId;
	private final Long concertSeatId;
	private final Integer price;
	private final Boolean isPaid;
	private final LocalDateTime expiresDate;

	public ConcertBookingResponse(ConcertBooking concertBooking) {
		this.id = concertBooking.getId();
		this.concertId = concertBooking.getConcert().getId();
		this.concertScheduleId = concertBooking.getConcertSchedule().getId();
		this.concertSeatId = concertBooking.getConcertSeat().getId();
		this.price = concertBooking.getPrice();
		this.isPaid = concertBooking.getPaid();
		this.expiresDate = concertBooking.getExpiresDate();
	}
}
