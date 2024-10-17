package hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.model.Concert;
import lombok.Getter;

@Getter
public class ConcertScheduleResponse {
	private Long id;
	private String title;
	private String description;
	private LocalDateTime bookingStartDate;
	private LocalDateTime bookingEndDate;
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
