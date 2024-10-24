package hhplus.hhplusconcertreservation.common.filter;

import java.io.IOException;

import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
import hhplus.hhplusconcertreservation.interfaces.presetation.exception.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WaitingQueueFilter implements Filter {
	private String waitingTokenSecretKey;


	public WaitingQueueFilter(String waitingTokenSecretKey) {
		this.waitingTokenSecretKey = waitingTokenSecretKey;
	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String header = httpRequest.getHeader("WaitingToken");
		System.out.println(header);
		try {
			String token = header.substring(7);

			SecretKey key = Keys.hmacShaKeyFor(waitingTokenSecretKey.getBytes());

			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			Long userId = claims.get("userId", Long.class);
			httpRequest.setAttribute("userId", userId);

			chain.doFilter(request, response);
		} catch (Exception e) {
			sendErrorResponse(httpResponse, ErrorType.WAITING_TOKEN_INVALID);
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
