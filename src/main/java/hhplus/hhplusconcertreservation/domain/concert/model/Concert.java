package hhplus.hhplusconcertreservation.domain.concert.model;

import java.time.LocalDateTime;

public class Concert {
	private Long id;
	private String title;
	private String description;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public Concert(Long id, String title, String description, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
