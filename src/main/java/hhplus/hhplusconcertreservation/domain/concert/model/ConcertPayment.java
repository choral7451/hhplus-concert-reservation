package hhplus.hhplusconcertreservation.domain.concert.model;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.User;

public class ConcertPayment {
	private Long id;
	private User user;
	private Concert concert;
	private ConcertSchedule concertSchedule;
	private ConcertSeat concertSeat;
	private ConcertBooking concertBooking;
	private Integer price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public ConcertPayment(ConcertBooking concertBooking) {
		this.id = concertBooking.getId();
		this.user = concertBooking.getUser();
		this.concert = concertBooking.getConcert();
		this.concertSchedule = concertBooking.getConcertSchedule();
		this.concertSeat = concertBooking.getConcertSeat();
		this.concertBooking = concertBooking;
		this.price = concertBooking.getPrice();
		this.createdDate = concertBooking.getCreatedDate();
		this.updatedDate = concertBooking.getUpdatedDate();
	}

	public ConcertPayment(Long id, User user, Concert concert, ConcertSchedule concertSchedule, ConcertSeat concertSeat,
		ConcertBooking concertBooking, Integer price, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.user = user;
		this.concert = concert;
		this.concertSchedule = concertSchedule;
		this.concertSeat = concertSeat;
		this.concertBooking = concertBooking;
		this.price = price;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Concert getConcert() {
		return concert;
	}

	public ConcertSchedule getConcertSchedule() {
		return concertSchedule;
	}

	public ConcertSeat getConcertSeat() {
		return concertSeat;
	}

	public ConcertBooking getConcertBooking() {
		return concertBooking;
	}

	public Integer getPrice() {
		return price;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
