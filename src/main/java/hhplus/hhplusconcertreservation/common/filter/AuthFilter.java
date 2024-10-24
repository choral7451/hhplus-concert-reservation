package hhplus.hhplusconcertreservation.common.filter;


import java.io.IOException;

import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
import hhplus.hhplusconcertreservation.interfaces.presetation.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter implements Filter {
	private String authTokenSecretKey;


	public AuthFilter(String authTokenSecretKey) {
		this.authTokenSecretKey = authTokenSecretKey;
	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authorizationHeader = httpRequest.getHeader("Authorization");
		try {
		String token = authorizationHeader.substring(7);

		SecretKey key = Keys.hmacShaKeyFor(authTokenSecretKey.getBytes());

		Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);

		chain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			sendErrorResponse(httpResponse, ErrorType.AUTHORIZATION_TOKEN_EXPIRED);
		} catch (Exception e) {
			sendErrorResponse(httpResponse, ErrorType.AUTHORIZATION_TOKEN_INVALID);
		}
	}

	private void sendErrorResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorResponse errorResponse = new ErrorResponse(errorType.getErrorCode(), errorType.getMessage());

		String jsonResponse = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(jsonResponse);
	}
}
