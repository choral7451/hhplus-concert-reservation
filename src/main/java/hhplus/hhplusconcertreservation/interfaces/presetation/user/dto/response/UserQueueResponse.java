package hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.enums.UserQueueResponseStatus;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.mapper.UserQueueStatusConverter;
import lombok.Getter;

@Getter
public class UserQueueResponse {
	private final Long id;
	private final Long userId;
	private final UserQueueResponseStatus status;
	private final Integer currentOrder;
	private final LocalDateTime expiresDate;

	public UserQueueResponse(UserQueue userQueue) {
		this.id = userQueue.getId();
		this.userId = userQueue.getUserId();
		this.status = UserQueueStatusConverter.toUserQueueResponseStatus(userQueue.getStatus());
		this.currentOrder = userQueue.getCurrentOrder();
		this.expiresDate = userQueue.getExpiresDate();
	}
}
