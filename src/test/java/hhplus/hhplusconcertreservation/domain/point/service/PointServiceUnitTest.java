package hhplus.hhplusconcertreservation.domain.point.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.point.repository.PointRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PointServiceUnitTest {
	@InjectMocks
	PointService pointService;

	@Mock
	private PointRepository pointRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenService tokenService;

	@Test
	public void 유저_포인트_조회() {
		// given
		Long givenUserId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 1000L, LocalDateTime.now(), LocalDateTime.now());

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenPoint));

		// when
		Point point = pointService.scanPoint("테스트토큰");


		// then
		assertEquals(point.getId(), givenPoint.getId());
		assertEquals(point.getUser().getId(), givenUserId);
		assertEquals(point.getAmount(), givenPoint.getAmount());

		verify(pointRepository, never()).save(givenUser);
	}

	@Test
	public void 유저_포인트_생성() {
		// given
		Long givenUserId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 0L, LocalDateTime.now(), LocalDateTime.now());

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
		when(pointRepository.save(givenUser)).thenReturn(givenPoint);

		// when
		Point point = pointService.scanPoint("테스트토큰");


		// then
		assertEquals(point.getId(), givenPoint.getId());
		assertEquals(point.getUser().getId(), givenUserId);
		assertEquals(point.getAmount(), givenPoint.getAmount());
	}

	@Test
	public void 존재하지_않는_유저_포인트_조회() {
		// given
		Long givenUserId = 1L;

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			pointService.scanPoint("테스트토큰");
		});

		assertEquals("USER_NOT_FOUND", exception.getMessage());

		// then
		verify(pointRepository, never()).findByUserId(anyLong());
	}

	@Test
	public void 유저_포인트_충전() {
		// given
		Long givenUserId = 1L;
		Long givenChargePoint = 1000L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenOriginPoint = new Point(1L, givenUser, 0L, LocalDateTime.now(), LocalDateTime.now());
		Point givenUpdatedPoint = new Point(1L, givenUser, givenOriginPoint.getAmount() + givenChargePoint, LocalDateTime.now(), LocalDateTime.now());

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenOriginPoint));
		when(pointRepository.update(any())).thenReturn(givenUpdatedPoint);

		// when
		Point point = pointService.chargePoint("테스트토큰", givenChargePoint);

		// then
		assertEquals(point.getId(), givenOriginPoint.getId());
		assertEquals(point.getUser().getId(), givenUserId);
		assertEquals(point.getAmount(), givenUpdatedPoint.getAmount());
	}

	@Test
	public void 존재하지_않는_유저_포인트_충전() {
		// given
		Long givenUserId = 1L;

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			pointService.scanPoint("테스트토큰");
		});

		assertEquals("USER_NOT_FOUND", exception.getMessage());

		// then
		verify(pointRepository, never()).findByUserId(anyLong());
		verify(pointRepository, never()).save(any());
		verify(pointRepository, never()).update(any());
	}

	@Test
	public void 포인트가_존재하지_않는_유저_포인트_충전() {
		// given
		Long givenUserId = 1L;
		Long givenChargePoint = 1000L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenOriginPoint = new Point(1L, givenUser, 0L, LocalDateTime.now(), LocalDateTime.now());
		Point givenUpdatedPoint = new Point(1L, givenUser, givenOriginPoint.getAmount() + givenChargePoint, LocalDateTime.now(), LocalDateTime.now());

		when(tokenService.getUserIdByAuthToken(anyString())).thenReturn(givenUserId);
		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
		when(pointRepository.save(any())).thenReturn(givenOriginPoint);
		when(pointRepository.update(any())).thenReturn(givenUpdatedPoint);

		// when
		Point point = pointService.chargePoint("테스트토큰", givenChargePoint);

		// then
		assertEquals(point.getId(), givenOriginPoint.getId());
		assertEquals(point.getUser().getId(), givenUserId);
		assertEquals(point.getAmount(), givenUpdatedPoint.getAmount());
	}
}