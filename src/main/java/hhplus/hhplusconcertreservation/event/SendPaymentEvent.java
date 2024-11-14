package hhplus.hhplusconcertreservation.event;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import lombok.Getter;

@Getter
public class SendPaymentEvent {
	private final String title;
	private final LocalDateTime performanceDate;
	private final Integer price;

	public SendPaymentEvent(ConcertBooking concertBooking) {
		this.title = concertBooking.getConcert().getTitle();
		this.performanceDate = concertBooking.getConcertSchedule().getPerformanceDate();
		this.price = concertBooking.getPrice();
	}
}
