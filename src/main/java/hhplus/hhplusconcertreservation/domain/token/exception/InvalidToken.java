package hhplus.hhplusconcertreservation.domain.token.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class InvalidToken extends CommonException {
	private static final String MESSAGE = "INVALID_TOKEN";

	public InvalidToken() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 401;
	}
}