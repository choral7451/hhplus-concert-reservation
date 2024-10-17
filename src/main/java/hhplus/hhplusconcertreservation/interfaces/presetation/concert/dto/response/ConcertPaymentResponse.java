package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;
import lombok.Getter;

@Getter
public class ConcertPaymentResponse {
	private final Long id;
	private final Long userId;
	private final Long concertId;
	private final Long concertScheduleId;
	private final Long concertSeatId;
	private final Long concertBookingId;
	private final Integer price;

	public ConcertPaymentResponse(ConcertPayment concertPayment) {
		this.id = concertPayment.getId();
		this.userId = concertPayment.getUser().getId();
		this.concertId = concertPayment.getConcert().getId();
		this.concertScheduleId = concertPayment.getConcertSchedule().getId();
		this.concertSeatId = concertPayment.getConcertSeat().getId();
		this.concertBookingId = concertPayment.getConcertBooking().getId();
		this.price = concertPayment.getPrice();
	}
}
