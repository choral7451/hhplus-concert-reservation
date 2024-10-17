package hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import lombok.Getter;

@Getter
public class UserQueueResponse {
	private final Long id;
	private final Long userId;
	private final int currentOrder;
	private final String token;
	private final LocalDateTime expiresDate;

	public UserQueueResponse(UserQueue userQueue) {
		this.id = userQueue.getId();
		this.userId = userQueue.getUserId();
		this.currentOrder = userQueue.getCurrentOrder();
		this.token = userQueue.getToken();
		this.expiresDate = userQueue.getExpiresDate();
	}
}
