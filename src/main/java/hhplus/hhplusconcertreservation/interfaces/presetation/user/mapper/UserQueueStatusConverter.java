package hhplus.hhplusconcertreservation.interfaces.presetation.user.mapper;

import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.enums.UserQueueResponseStatus;

public class UserQueueStatusConverter {
	static public UserQueueResponseStatus toUserQueueResponseStatus(UserQueueStatus status) {
		return UserQueueResponseStatus.valueOf(status.name());
	}
}
