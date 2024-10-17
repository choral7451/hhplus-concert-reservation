package hhplus.hhplusconcertreservation.domain.concert.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class AlreadyBookedSeat extends CommonException {
	private static final String MESSAGE = "ALREADY_BOOKED_SEAT";

	public AlreadyBookedSeat() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}