package hhplus.hhplusconcertreservation.interfaces.api.concert.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertResponse {
	private Long concertId;
	private Long concertDateId;
	private LocalDateTime performanceDate;
	private String title;
	private String place;
}
