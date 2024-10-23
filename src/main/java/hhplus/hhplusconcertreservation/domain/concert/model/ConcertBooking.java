package hhplus.hhplusconcertreservation.domain.concert.model;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.concert.exception.ReservationExpired;
import hhplus.hhplusconcertreservation.domain.user.model.User;

public class ConcertBooking {
	private Long id;
	private User user;
	private Concert concert;
	private ConcertSchedule concertSchedule;
	private ConcertSeat concertSeat;
	private Integer price;
	private Boolean isPaid;
	private LocalDateTime expiresDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public ConcertBooking(User user, ConcertSeat concertSeat) {
		this.user = user;
		this.concert = concertSeat.getConcert();
		this.concertSchedule = concertSeat.getConcertSchedule();
		this.concertSeat = concertSeat;
		this.price = concertSeat.getPrice();
		this.isPaid = false;
		this.expiresDate = LocalDateTime.now().plusMinutes(5);
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	public ConcertBooking(Long id, User user, Concert concert, ConcertSchedule concertSchedule, ConcertSeat concertSeat,
		Integer price, Boolean isPaid, LocalDateTime expiresDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.user = user;
		this.concert = concert;
		this.concertSchedule = concertSchedule;
		this.concertSeat = concertSeat;
		this.price = price;
		this.isPaid = isPaid;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public ConcertBooking outBox() {
		this.isPaid = true;
		return this;
	}

	public void validateReservationExpiration() {
		if(this.getExpiresDate().isBefore(LocalDateTime.now())) {
			throw new ReservationExpired();
		}
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

	public Integer getPrice() {
		return price;
	}

	public Boolean getPaid() {
		return isPaid;
	}

	public LocalDateTime getExpiresDate() {
		return expiresDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
