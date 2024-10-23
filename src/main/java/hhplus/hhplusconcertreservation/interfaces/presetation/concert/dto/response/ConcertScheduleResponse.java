package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.model.Concert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ConcertScheduleResponse {
	@Schema(type = "Number", description = "공연 일정 아이디", defaultValue = "1")
	private Long id;

	@Schema(type = "String", description = "공연 제목", defaultValue = "자우림 콘서트")
	private String title;

	@Schema(type = "String", description = "공연 설명", defaultValue = "밴드와 같이 즐기는 공연")
	private String description;

	@Schema(type = "Date", description = "예약 시작 시간", defaultValue = "2024-10-17T23:59:53.864Z")
	private LocalDateTime bookingStartDate;

	@Schema(type = "Date", description = "예약 종료 시간", defaultValue = "2024-10-17T23:59:53.864Z")
	private LocalDateTime bookingEndDate;

	@Schema(type = "Date", description = "공연 시작 시간", defaultValue = "2024-10-17T23:59:53.864Z")
	private LocalDateTime performanceDate;

	public ConcertScheduleResponse(Long id, Concert concert, LocalDateTime bookingStartDate,
		LocalDateTime bookingEndDate, LocalDateTime performanceDate) {
		this.id = id;
		this.title = concert.getTitle();
		this.description = concert.getDescription();
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
		this.performanceDate = performanceDate;
	}
}
