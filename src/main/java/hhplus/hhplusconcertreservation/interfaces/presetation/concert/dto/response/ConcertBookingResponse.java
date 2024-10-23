package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ConcertBookingResponse {
	@Schema(type = "Number", description = "예약 아이디", defaultValue = "1")
	private final Long id;

	@Schema(type = "Number", description = "콘서트 아이디", defaultValue = "1")
	private final Long concertId;

	@Schema(type = "Number", description = "공연 일정 아이디", defaultValue = "1")
	private final Long concertScheduleId;

	@Schema(type = "Number", description = "공연 좌석 아이디", defaultValue = "1")
	private final Long concertSeatId;

	@Schema(type = "Number", description = "좌석 가격", defaultValue = "1000")
	private final Integer price;

	@Schema(type = "Number", description = "결제 여부", defaultValue = "false")
	private final Boolean isPaid;

	@Schema(type = "Number", description = "결제 가능 만료 시간", defaultValue = "2024-10-17T23:59:53.864Z")
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
