package hhplus.hhplusconcertreservation.domain.common.exception;

import org.springframework.boot.logging.LogLevel;

import lombok.Getter;

@Getter
public enum ErrorType {
	// concert
	NOT_FOUND_CONCERT_BOOKING(ErrorCode.NOT_FOUND, "NOT_FOUND_CONCERT_BOOKING", LogLevel.WARN),
	ALREADY_BOOKED_SEAT(ErrorCode.CLIENT_ERROR, "ALREADY_BOOKED_SEAT", LogLevel.INFO),
	ALREADY_PAID_SEAT(ErrorCode.CLIENT_ERROR, "ALREADY_PAID_SEAT", LogLevel.INFO),
	RESERVATION_EXPIRED(ErrorCode.CLIENT_ERROR, "RESERVATION_EXPIRED", LogLevel.INFO),
	UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE(ErrorCode.NOT_FOUND, "UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE", LogLevel.INFO),
	UNABLE_TO_RETRIEVE_CONCERT_SEAT(ErrorCode.NOT_FOUND, "UNABLE_TO_RETRIEVE_CONCERT_SEAT", LogLevel.INFO),

	// token
	INVALID_TOKEN(ErrorCode.CLIENT_ERROR, "INVALID_TOKEN", LogLevel.INFO),

	// POINT
	INSUFFICIENT_POINTS(ErrorCode.CLIENT_ERROR, "INSUFFICIENT_POINTS", LogLevel.INFO),
	POINT_NOT_FOUND(ErrorCode.NOT_FOUND, "POINT_NOT_FOUND", LogLevel.WARN),

	// USER
	USER_NOT_FOUND(ErrorCode.NOT_FOUND, "USER_NOT_FOUND", LogLevel.WARN),
	USER_QUEUE_NOT_FOUND(ErrorCode.NOT_FOUND, "USER_QUEUE_NOT_FOUND", LogLevel.WARN);

	private final ErrorCode errorCode;
	private final String message;
	private final LogLevel logLevel;

	ErrorType(ErrorCode errorCode, String message, LogLevel logLevel) {
		this.errorCode = errorCode;
		this.message = message;
		this.logLevel = logLevel;
	}
}
