package hhplus.hhplusconcertreservation.interfaces.common.dto;

import hhplus.hhplusconcertreservation.domain.common.exception.ErrorCode;

public record ErrorResponse (
	ErrorCode code,
	String message
){}
