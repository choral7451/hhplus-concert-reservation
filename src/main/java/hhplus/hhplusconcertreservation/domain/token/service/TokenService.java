package hhplus.hhplusconcertreservation.domain.token.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.token.exception.InvalidToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	@Value("${variables.waitingTokenSecretKey}")
	private String waitingTokenSecretKey;

	public String issueWaitingToken(Long userId) {
		SecretKey key = Keys.hmacShaKeyFor(waitingTokenSecretKey.getBytes());

		return Jwts.builder()
			.claim("userId", userId)
			.signWith(key)
			.compact();
	}

	public Long getUserIdByWaitingToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(waitingTokenSecretKey.getBytes());

			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			return claims.get("userId", Long.class);
		} catch (Exception e) {
			log.error("Invalid token", e);
			throw new InvalidToken();
		}
	}
}
