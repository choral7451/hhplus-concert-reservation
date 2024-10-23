package hhplus.hhplusconcertreservation.domain.user.service.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class UserQueueNotFound extends CommonException {
	private static final String MESSAGE = "USER_QUEUE_NOT_FOUND";

	public UserQueueNotFound() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}