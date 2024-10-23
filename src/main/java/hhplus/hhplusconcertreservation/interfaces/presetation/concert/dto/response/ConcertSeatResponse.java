package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ConcertSeatResponse {
	@Schema(type = "Number", description = "공연 좌석 아이디", defaultValue = "1")
	private final Long id;

	@Schema(type = "Number", description = "좌석 번호", defaultValue = "1")
	private final Integer number;

	@Schema(type = "Number", description = "좌석 가격", defaultValue = "1000")
	private final Integer price;

	@Schema(type = "Number", description = "좌석 결제 여부", defaultValue = "false")
	private final Boolean isPaid;

	public ConcertSeatResponse(ConcertSeat concertSeat) {
		this.id = concertSeat.getId();
		this.number = concertSeat.getNumber();
		this.price = concertSeat.getPrice();
		this.isPaid = concertSeat.getPaid();
	}
}
