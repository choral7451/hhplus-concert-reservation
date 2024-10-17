package hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatResponse {
	private Long seatId;
	private Long seatNumber;
	private Long price;
	private Boolean isReserved;
}
