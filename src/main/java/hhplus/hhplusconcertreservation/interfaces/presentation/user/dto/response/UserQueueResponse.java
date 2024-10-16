package hhplus.hhplusconcertreservation.interfaces.presentation.user.dto.response;

import java.time.LocalDateTime;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserQueueResponse {
	@Schema(description = "유저 대기 아이디", defaultValue = "1")
	private final Long id;

	@Schema(description = "유저 아이디", defaultValue = "1")
	private final Long userId;

	@Schema(description = "대기 순서", defaultValue = "5")
	private final Long currentOrder;

	@Schema(description = "충전 완료 후 남은 금액", defaultValue = "sdfjsdklfjadkslfjelqwnjklsand")
	private final String token;

	@Schema(description = "충전 완료 후 남은 금액", defaultValue = "2024-10-16T16:38:30.338Z")
	private final LocalDateTime expiresDate;

	@Builder
	public UserQueueResponse(UserQueue userQueue, Long currentOrder) {
		this.id = userQueue.getId();
		this.userId = userQueue.getUserId();
		this.currentOrder = currentOrder;
		this.token = userQueue.getToken();
		this.expiresDate = userQueue.getExpiresDate();
	}
}
