package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import lombok.Getter;

@Getter
public class ConcertSeatsResponse {
	private final Long id;
	private final Integer number;
	private final Integer price;
	private final Boolean isPaid;

	public ConcertSeatsResponse(ConcertSeat concertSeat) {
		this.id = concertSeat.getId();
		this.number = concertSeat.getNumber();
		this.price = concertSeat.getPrice();
		this.isPaid = concertSeat.getPaid();
	}
}
