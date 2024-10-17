package hhplus.hhplusconcertreservation.infrastructure.user.mapper;

import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.infrastructure.user.enums.UserQueueEntityStatus;

public class UserQueueStatusMapper {
	static public UserQueueStatus toUserQueueStatus(UserQueueEntityStatus status) {
		return UserQueueStatus.valueOf(status.name());
	}

	static public UserQueueEntityStatus toUserQueueEntityStatus(UserQueueStatus status) {
		return UserQueueEntityStatus.valueOf(status.name());
	}
}
