package hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateUserQueueRequest {
	@Schema(type = "Number", description = "유저 아이디", defaultValue = "1")
	private Long userId;
}
