package hhplus.hhplusconcertreservation.domain.user.model;

import java.time.LocalDateTime;

public class User {
	private Long id;
	private String name;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public User(Long id, String name, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.name = name;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
