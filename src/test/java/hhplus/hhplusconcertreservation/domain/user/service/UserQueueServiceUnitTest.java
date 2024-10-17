package hhplus.hhplusconcertreservation.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserNotFound;

@ExtendWith(MockitoExtension.class)
class UserQueueServiceUnitTest {


	@InjectMocks
	private UserQueueService userQueueService;

	@Mock
	private TokenService tokenService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserQueueRepository userQueueRepository;

	@Test
	public void 유저가_대기열에_존재하는_경우_토큰_조회() {
		// given
		User givenUser = new User(1L, "테스트", LocalDateTime.now(), LocalDateTime.now());
		UserQueue givenUserQueue = new UserQueue(givenUser.getId(), "testToken");

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(userQueueRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUserQueue));

		// when
		String token = userQueueService.issueToken(givenUser.getId());

		Assertions.assertEquals(givenUserQueue.getToken(), token);

		verify(tokenService, never()).issueToken(anyLong());
		verify(userQueueRepository, never()).save(anyLong(), anyString());
	}

	@Test
	public void 유저가_대기열에_존재하지_않는_경우_토큰_발행() {
		// given
		User givenUser = new User(1L, "테스트", LocalDateTime.now(), LocalDateTime.now());
		UserQueue givenUserQueue = new UserQueue(givenUser.getId(), "testToken");

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(userQueueRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
		when(tokenService.issueToken(anyLong())).thenReturn(givenUserQueue.getToken());
		when(userQueueRepository.save(anyLong(), anyString())).thenReturn(givenUserQueue);

		// when
		String token = userQueueService.issueToken(givenUser.getId());

		Assertions.assertEquals(givenUserQueue.getToken(), token);
	}

	@Test
	public void 존재하지_않는_유저_토큰_발행_요청() {
		// given
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		UserNotFound exception = assertThrows(UserNotFound.class, () -> {
			userQueueService.issueToken(1L);
		});

		assertEquals("USER_NOT_FOUND", exception.getMessage());

		verify(userQueueRepository, never()).findByUserId(anyLong());
		verify(userQueueRepository, never()).save(anyLong(), anyString());
		verify(tokenService, never()).issueToken(anyLong());
	}
}