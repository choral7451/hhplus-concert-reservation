package hhplus.hhplusconcertreservation.domain.point.model;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.User;

public class Point {
	private final Long id;
	private final User user;
	private final Long amount;
	private final LocalDateTime createdDate;
	private final LocalDateTime updatedDate;

	public Point(Long id, User user, Long amount, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.user = user;
		this.amount = amount;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Long getAmount() {
		return amount;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
