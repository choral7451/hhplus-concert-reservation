package hhplus.hhplusconcertreservation.domain.token.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import hhplus.hhplusconcertreservation.domain.token.exception.InvalidToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class TokenServiceUnitTest {
	@InjectMocks
	private TokenService tokenService;

	@Test
	public void 토큰_발행() {
		// given
		ReflectionTestUtils.setField(tokenService, "secretKey", "mySuperSecretKeyForJwtSigningWhichIsLongEnoughTest");

		// when & then
		assertNotNull(tokenService.issueToken(1L));
	}

	@Test
	public void 토큰_유저_아이디_추출() {
		// given
		String givenSecretKey = "mySuperSecretKeyForJwtSigningWhichIsLongEnoughTest";
		Long givenUserId = 1L;
		SecretKey key = Keys.hmacShaKeyFor(givenSecretKey.getBytes());

		String givenToken = Jwts.builder()
			.claim("userId", givenUserId)
			.signWith(key)
			.compact();

		ReflectionTestUtils.setField(tokenService, "secretKey", givenSecretKey);

		// when
		Long userId = tokenService.getUserId(givenToken);

		// then
		assertEquals(givenUserId, userId);
	}

	@Test
	public void 유효하지_않은_토큰_유저_아이디_추출() {
		// given
		String givenInvalidToken = "invalidToken";

		// when
		InvalidToken exception = assertThrows(InvalidToken.class, () -> {
			tokenService.getUserId(givenInvalidToken);
		});

		// then
		assertEquals("INVALID_TOKEN", exception.getMessage());
	}
}