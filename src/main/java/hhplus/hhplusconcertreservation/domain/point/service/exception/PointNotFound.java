package hhplus.hhplusconcertreservation.domain.point.service.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class PointNotFound extends CommonException {
	private static final String MESSAGE = "POINT_NOT_FOUND";

	public PointNotFound() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}