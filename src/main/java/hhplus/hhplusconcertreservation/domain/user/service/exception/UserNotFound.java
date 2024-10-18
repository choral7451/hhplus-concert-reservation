package hhplus.hhplusconcertreservation.domain.user.service.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class UserNotFound extends CommonException {
	private static final String MESSAGE = "USER_NOT_FOUND";

	public UserNotFound() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}