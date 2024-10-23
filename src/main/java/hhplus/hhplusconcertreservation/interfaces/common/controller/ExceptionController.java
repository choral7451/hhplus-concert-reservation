package hhplus.hhplusconcertreservation.interfaces.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.interfaces.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
	@ExceptionHandler(CoreException.class)
	public ResponseEntity<Object> handleCoreException(CoreException e) {

		switch (e.getErrorType().getLogLevel()) {
			case ERROR -> log.error("{}, Payload: {}", e.getMessage(), e.getPayload());
			case WARN -> log.warn("{}, Payload: {}", e.getMessage(), e.getPayload());
			default -> log.info("{}, Payload: {}", e.getMessage(), e.getPayload());
		}

		HttpStatus status;
		switch (e.getErrorType().getErrorCode()) {
			case DB_ERROR -> status = HttpStatus.INTERNAL_SERVER_ERROR;
			case CLIENT_ERROR -> status = HttpStatus.BAD_REQUEST;
			case NOT_FOUND -> status = HttpStatus.NOT_FOUND;
			default -> status = HttpStatus.OK;
		}

		ErrorResponse errorResponse = new ErrorResponse(e.getErrorType().getErrorCode(), e.getMessage());
		return new ResponseEntity<>(errorResponse, status);
	}

}
