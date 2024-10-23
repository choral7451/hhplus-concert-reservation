package hhplus.hhplusconcertreservation.domain.concert.model;

import java.time.LocalDateTime;

public class ConcertSchedule {
	private Long id;
	private Concert concert;
	private LocalDateTime bookingStartDate;
	private LocalDateTime bookingEndDate;
	private LocalDateTime performanceDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public ConcertSchedule(Long id, Concert concert, LocalDateTime bookingStartDate, LocalDateTime bookingEndDate,
		LocalDateTime performanceDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.concert = concert;
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
		this.performanceDate = performanceDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public Concert getConcert() {
		return concert;
	}

	public LocalDateTime getBookingStartDate() {
		return bookingStartDate;
	}

	public LocalDateTime getBookingEndDate() {
		return bookingEndDate;
	}

	public LocalDateTime getPerformanceDate() {
		return performanceDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
