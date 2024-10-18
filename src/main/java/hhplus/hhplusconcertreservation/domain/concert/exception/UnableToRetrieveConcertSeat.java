package hhplus.hhplusconcertreservation.domain.concert.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.CommonException;

public class UnableToRetrieveConcertSeat extends CommonException {
	private static final String MESSAGE = "UNABLE_TO_RETRIEVE_CONCERT_SEAT";

	public UnableToRetrieveConcertSeat() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 403;
	}
}