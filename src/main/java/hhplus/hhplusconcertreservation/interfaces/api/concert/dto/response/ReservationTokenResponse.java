package hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationTokenResponse {
	private String token;
}