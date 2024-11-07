package hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.enums.UserQueueResponseStatus;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.mapper.UserQueueStatusConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserQueueResponse {
	@Schema(type = "String", description = "대기 상태값", defaultValue = "WAITING")
	private final UserQueueResponseStatus status;

	@Schema(type = "Number", description = "대기 순서", defaultValue = "5")
	private final Long currentOrder;

	public UserQueueResponse(UserQueue userQueue) {
		this.status = UserQueueStatusConverter.toUserQueueResponseStatus(userQueue.getStatus());
		this.currentOrder = userQueue.getCurrentOrder();
	}
}
