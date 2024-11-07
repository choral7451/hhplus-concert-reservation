package hhplus.hhplusconcertreservation.domain.user.model;

import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;

public class UserQueue {
	private UserQueueStatus status;
	private Long currentOrder;

	public UserQueue(UserQueueStatus status, Long currentOrder) {
		this.status = status;
		this.currentOrder = currentOrder;
	}

	static public UserQueue setActive() {
		return new UserQueue(UserQueueStatus.ACTIVATION, 0L);
	}

	static public UserQueue setWaiting(Long order) {
		return new UserQueue(UserQueueStatus.WAITING, order);
	}

	public UserQueueStatus getStatus() {
		return status;
	}

	public Long getCurrentOrder() {
		return currentOrder;
	}
}
