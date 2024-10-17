package hhplus.hhplusconcertreservation.domain.concert.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class ConcertBookingNotFound extends CommonException {
	private static final String MESSAGE = "CONCERT_BOOKING_NOT_FOUND";

	public ConcertBookingNotFound() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}