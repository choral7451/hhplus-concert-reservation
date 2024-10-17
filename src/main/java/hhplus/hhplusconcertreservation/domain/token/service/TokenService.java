package hhplus.hhplusconcertreservation.domain.token.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	public String issueToken(Long userId) {
		return Jwts.builder()
			.claim("userId", userId)
			.compact();
	}
}
