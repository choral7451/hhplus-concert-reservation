package hhplus.hhplusconcertreservation.domain.concert.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.hhplusconcertreservation.domain.concert.exception.UnableToRetrieveConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.model.Concert;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertScheduleRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;

@ExtendWith(MockitoExtension.class)
class ConcertServiceUnitTest {

	@InjectMocks
	private ConcertService concertService;

	@Mock
	private TokenService tokenService;

	@Mock
	private ConcertScheduleRepository concertScheduleRepository;

	@Mock
	private UserQueueRepository userQueueRepository;

	@Test
	public void 권한이_존재하는_유저_공연_일정_전체_조회() {
		// given
		Long givenUserId = 1L;
		String givenJwtToken = "testToken";
		UserQueue givenUserQueue = new UserQueue(1L, givenUserId, UserQueueStatus.ACTIVATION, givenJwtToken,  LocalDateTime.now().plusMinutes(5), LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		List<ConcertSchedule> givenConcertSchedules = List.of(
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSchedule(2L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSchedule(3L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now())
			);

		when(tokenService.getUserId(anyString())).thenReturn(givenUserId);
		when(userQueueRepository.findActiveUserQueueByUserId(anyLong())).thenReturn(Optional.of(givenUserQueue));
		when(concertScheduleRepository.findAllBookableSchedulesByConcertId(anyLong())).thenReturn(givenConcertSchedules);

		// when
		List<ConcertSchedule> concertSchedules = concertService.scanAllBookableConcertSchedules(givenJwtToken, givenUserId);

		// then
		assertEquals(3, concertSchedules.size());

		for (int i = 0; i < concertSchedules.size(); i++) {
			ConcertSchedule givenConcertSchedule = concertSchedules.get(i);
			ConcertSchedule concertSchedule = givenConcertSchedules.get(i);

			assertEquals(concertSchedule.getId(), givenConcertSchedule.getId());
			assertEquals(concertSchedule.getConcert(), givenConcertSchedule.getConcert());
			assertEquals(concertSchedule.getBookingStartDate(), givenConcertSchedule.getBookingStartDate());
			assertEquals(concertSchedule.getBookingEndDate(), givenConcertSchedule.getBookingEndDate());
			assertEquals(concertSchedule.getPerformanceDate(), givenConcertSchedule.getPerformanceDate());
			assertEquals(concertSchedule.getCreatedDate(), givenConcertSchedule.getCreatedDate());
			assertEquals(concertSchedule.getUpdatedDate(), givenConcertSchedule.getUpdatedDate());
		}
	}

	@Test
	public void 권한이_없는_유저_공연_일정_전체_조회() {
		// given
		Long givenUserId = 1L;
		String givenJwtToken = "testToken";

		when(tokenService.getUserId(anyString())).thenReturn(givenUserId);
		when(userQueueRepository.findActiveUserQueueByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		UnableToRetrieveConcertSchedule exception = assertThrows(UnableToRetrieveConcertSchedule.class, () -> {
			concertService.scanAllBookableConcertSchedules(givenJwtToken, givenUserId);
		});

		assertEquals("UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE", exception.getMessage());

		// then
		verify(concertScheduleRepository, never()).findAllBookableSchedulesByConcertId(anyLong());
	}
}