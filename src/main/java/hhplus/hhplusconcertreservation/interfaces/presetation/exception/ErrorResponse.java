package hhplus.hhplusconcertreservation.interfaces.presetation.exception;

import hhplus.hhplusconcertreservation.domain.common.exception.ErrorCode;

public record ErrorResponse (
	ErrorCode code,
	String message
){}
