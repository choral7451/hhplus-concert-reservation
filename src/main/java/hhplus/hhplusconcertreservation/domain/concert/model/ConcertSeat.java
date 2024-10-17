package hhplus.hhplusconcertreservation.domain.concert.model;

import java.time.LocalDateTime;

public class ConcertSeat {
	private Long id;
	private Concert concert;
	private ConcertSchedule concertSchedule;
	private Integer number;
	private Integer price;
	private Boolean isPaid;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public ConcertSeat(Long id, Concert concert, ConcertSchedule concertSchedule, Integer number, Integer price,
		Boolean isPaid, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.concert = concert;
		this.concertSchedule = concertSchedule;
		this.number = number;
		this.price = price;
		this.isPaid = isPaid;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public Concert getConcert() {
		return concert;
	}

	public ConcertSchedule getConcertSchedule() {
		return concertSchedule;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getPrice() {
		return price;
	}

	public Boolean getPaid() {
		return isPaid;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}