package hhplus.hhplusconcertreservation.domain.concert.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class AlreadyPaidSeat extends CommonException {
	private static final String MESSAGE = "ALREADY_PAID_SEAT";

	public AlreadyPaidSeat() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}