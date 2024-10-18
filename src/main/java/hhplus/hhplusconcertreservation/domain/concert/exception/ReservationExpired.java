package hhplus.hhplusconcertreservation.domain.concert.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class ReservationExpired extends CommonException {
	private static final String MESSAGE = "RESERVATION_EXPIRED";

	public ReservationExpired() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}