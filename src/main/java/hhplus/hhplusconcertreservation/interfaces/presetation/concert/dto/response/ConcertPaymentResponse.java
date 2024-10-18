package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ConcertPaymentResponse {
	@Schema(type = "Number", description = "결제 아이디", defaultValue = "1")
	private final Long id;

	@Schema(type = "Number", description = "유저 아이디", defaultValue = "1")
	private final Long userId;

	@Schema(type = "Number", description = "공연 아이디", defaultValue = "1")
	private final Long concertId;

	@Schema(type = "Number", description = "공연 일정 아이디", defaultValue = "1")
	private final Long concertScheduleId;

	@Schema(type = "Number", description = "공연 좌석 아이디", defaultValue = "1")
	private final Long concertSeatId;

	@Schema(type = "Number", description = "공연 예약 아이디", defaultValue = "1")
	private final Long concertBookingId;

	@Schema(type = "Number", description = "좌석 가격", defaultValue = "1000")
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
