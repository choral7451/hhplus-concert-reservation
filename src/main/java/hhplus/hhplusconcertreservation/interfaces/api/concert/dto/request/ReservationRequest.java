package hhplus.hhplusconcertreservation.interfaces.api.concert.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRequest {
	private Long userId;
	private Long seatId;
}
