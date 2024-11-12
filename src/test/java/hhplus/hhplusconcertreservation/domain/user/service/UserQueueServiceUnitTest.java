package hhplus.hhplusconcertreservation.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.token.repository.TokenRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserQueueServiceUnitTest {

	@InjectMocks
	private UserQueueService userQueueService;

	@Mock
	private TokenService tokenService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private ObjectMapper objectMapper;


	@Test
	public void 유저_대기_토큰_발급() {
		// given
		User givenUser = new User(1L, "테스트", LocalDateTime.now(), LocalDateTime.now());

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));

		// when
		String token = userQueueService.issueToken(givenUser.getId());

		assertNotNull(token);
	}

	@Test
	public void 존재하지_않는_유저_토큰_발행_요청() {
		// given
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			userQueueService.issueToken(1L);
		});

		assertEquals("USER_NOT_FOUND", exception.getMessage());
	}

	@Test
	public void 유저의_대기열_상태_확인() throws IOException {
		// given
		Long givenUserId = 1L;
		Long givenOrder = 10L;
		String givenToken = "토큰";
		String givenTokenJson = "테스트토큰제이슨";
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(tokenRepository.hasActiveToken(anyString())).thenReturn(false);
		when(objectMapper.writeValueAsString(any())).thenReturn(givenTokenJson);
		when(tokenRepository.countCurrentOrderByToken(anyString())).thenReturn(givenOrder);

		// when
		UserQueue userQueue = userQueueService.scanUserQueue(givenUserId, givenToken);

		assertEquals(UserQueueStatus.WAITING, userQueue.getStatus());
		assertEquals(givenOrder, userQueue.getCurrentOrder());
	}

	@Test
	public void 유저가_대기열에_포함되어있지_않습니다() throws IOException {
		// given
		Long givenUserId = 1L;
		String givenToken = "토큰";
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		String givenTokenJson = "테스트토큰제이슨";

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(tokenRepository.hasActiveToken(anyString())).thenReturn(false);
		when(objectMapper.writeValueAsString(any())).thenReturn(givenTokenJson);
		when(tokenRepository.countCurrentOrderByToken(anyString())).thenReturn(null);

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			userQueueService.scanUserQueue(givenUserId, givenToken);
		});

		assertEquals("USER_QUEUE_NOT_FOUND", exception.getMessage());
	}
}