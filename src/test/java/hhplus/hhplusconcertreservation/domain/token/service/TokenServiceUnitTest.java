package hhplus.hhplusconcertreservation.domain.token.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class TokenServiceUnitTest {
	@InjectMocks
	private TokenService tokenService;

	@Test
	public void 토큰_발행() {
		// given
		ReflectionTestUtils.setField(tokenService, "waitingTokenSecretKey", "mySuperSecretKeyForJwtSigningWhichIsLongEnoughTest");

		// when & then
		assertNotNull(tokenService.issueWaitingToken(1L));
	}

	@Test
	public void 대기열_토큰_유저_아이디_추출() {
		// given
		String givenSecretKey = "mySuperSecretKeyForJwtSigningWhichIsLongEnoughTest";
		Long givenUserId = 1L;
		SecretKey key = Keys.hmacShaKeyFor(givenSecretKey.getBytes());

		String givenToken = Jwts.builder()
			.claim("userId", givenUserId)
			.signWith(key)
			.compact();

		ReflectionTestUtils.setField(tokenService, "waitingTokenSecretKey", givenSecretKey);

		// when
		Long userId = tokenService.getUserIdByWaitingToken(givenToken);

		// then
		assertEquals(givenUserId, userId);
	}

	@Test
	public void 유효하지_않은_대기열_토큰_유저_아이디_추출() {
		// given
		String givenInvalidToken = "invalidToken";

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			tokenService.getUserIdByWaitingToken(givenInvalidToken);
		});

		// then
		assertEquals("WAITING_TOKEN_INVALID", exception.getMessage());
	}

	@Test
	public void 인증_인가_토큰_유저_아이디_추출() {
		// given
		String givenSecretKey = "mySuperSecretKeyForJwtSigningWhichIsLongEnoughTest";
		Long givenUserId = 1L;
		SecretKey key = Keys.hmacShaKeyFor(givenSecretKey.getBytes());

		String givenToken = Jwts.builder()
			.claim("userId", givenUserId)
			.signWith(key)
			.compact();

		ReflectionTestUtils.setField(tokenService, "authTokenSecretKey", givenSecretKey);

		// when
		Long userId = tokenService.getUserIdByAuthToken(givenToken);

		// then
		assertEquals(givenUserId, userId);
	}

	@Test
	public void 유효하지_않은_인증_인가_토큰_유저_아이디_추출() {
		// given
		String givenInvalidToken = "invalidToken";

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			tokenService.getUserIdByWaitingToken(givenInvalidToken);
		});

		// then
		assertEquals("WAITING_TOKEN_INVALID", exception.getMessage());
	}
}