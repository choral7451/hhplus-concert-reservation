package hhplus.hhplusconcertreservation.domain.token.service;

import java.util.Date;

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

	@Value("${variables.authTokenSecretKey}")
	private String authTokenSecretKey;

	public String issueWaitingToken(Long userId) {
		SecretKey key = Keys.hmacShaKeyFor(waitingTokenSecretKey.getBytes());

		return Jwts.builder()
			.claim("userId", userId)
			.signWith(key)
			.compact();
	}

	public String issueAuthToken(Long userId) {
		SecretKey key = Keys.hmacShaKeyFor(authTokenSecretKey.getBytes());

		return Jwts.builder()
			.claim("userId", userId)
			.signWith(key)
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
			.compact();
	}

	public Long getUserIdByAuthToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(authTokenSecretKey.getBytes());

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
