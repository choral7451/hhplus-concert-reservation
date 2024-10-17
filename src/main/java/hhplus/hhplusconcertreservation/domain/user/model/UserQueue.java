package hhplus.hhplusconcertreservation.domain.user.model;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.infrastructure.user.enums.UserQueueEntityStatus;

public class UserQueue {
	private Long id;
	private Long userId;
	private UserQueueEntityStatus status;
	private String token;
	private Integer currentOrder;
	private LocalDateTime expiresDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public UserQueue(Long userId, String token) {
		this.userId = userId;
		this.status = UserQueueEntityStatus.WAITING;
		this.token = token;
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	public UserQueue(Long id, Long userId, UserQueueEntityStatus status, String token, LocalDateTime expiresDate,
		LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.token = token;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public UserQueue setCurrentOrder(int order) {
		this.currentOrder = order;

		return this;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public UserQueueEntityStatus getStatus() {
		return status;
	}

	public String getToken() {
		return token;
	}

	public Integer getCurrentOrder() {
		return currentOrder;
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
