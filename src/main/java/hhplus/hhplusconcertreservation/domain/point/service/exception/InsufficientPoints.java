package hhplus.hhplusconcertreservation.domain.point.service.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class InsufficientPoints extends CommonException {
	private static final String MESSAGE = "INSUFFICIENT_POINTS";

	public InsufficientPoints() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}