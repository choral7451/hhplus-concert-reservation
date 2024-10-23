package hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.enums.UserQueueResponseStatus;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.mapper.UserQueueStatusConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserQueueResponse {
	@Schema(type = "Number", description = "대기열 아이디", defaultValue = "1")
	private final Long id;

	@Schema(type = "Number", description = "유저 아이디", defaultValue = "1")
	private final Long userId;

	@Schema(type = "String", description = "대기 상태값", defaultValue = "WAITING")
	private final UserQueueResponseStatus status;

	@Schema(type = "Number", description = "대기 순서", defaultValue = "5")
	private final Integer currentOrder;

	@Schema(type = "Date", description = "활성화 만료 시간", defaultValue = "2024-10-17T23:59:53.864Z")
	private final LocalDateTime expiresDate;

	public UserQueueResponse(UserQueue userQueue) {
		this.id = userQueue.getId();
		this.userId = userQueue.getUserId();
		this.status = UserQueueStatusConverter.toUserQueueResponseStatus(userQueue.getStatus());
		this.currentOrder = userQueue.getCurrentOrder();
		this.expiresDate = userQueue.getExpiresDate();
	}
}
