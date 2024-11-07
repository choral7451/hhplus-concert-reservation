package hhplus.hhplusconcertreservation.domain.concert.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.concert.model.Concert;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertBookingRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertPaymentRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertScheduleRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertSeatRepository;
import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.point.repository.PointRepository;
import hhplus.hhplusconcertreservation.domain.token.repository.TokenRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ConcertServiceUnitTest {

	@InjectMocks
	private ConcertService concertService;

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private ConcertScheduleRepository concertScheduleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ConcertSeatRepository concertSeatRepository;

	@Mock
	private ConcertBookingRepository concertBookingRepository;

	@Mock
	private PointRepository pointRepository;

	@Mock
	private ConcertPaymentRepository concertPaymentRepository;

	@Test
	public void 권한이_존재하는_유저_공연_일정_전체_조회() {
		// given
		Long givenUserId = 1L;
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		List<ConcertSchedule> givenConcertSchedules = List.of(
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSchedule(2L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSchedule(3L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now())
			);

		when(tokenRepository.hasActiveToken(anyString())).thenReturn(true);
		when(concertScheduleRepository.findAllBookableSchedulesByConcertId(anyLong())).thenReturn(givenConcertSchedules);

		// when
		List<ConcertSchedule> concertSchedules = concertService.scanAllBookableConcertSchedules(givenUserId, givenUserId);

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

		when(tokenRepository.hasActiveToken(anyString())).thenReturn(false);

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.scanAllBookableConcertSchedules(givenUserId, givenUserId);
		});

		assertEquals("UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE", exception.getMessage());

		// then
		verify(concertScheduleRepository, never()).findAllBookableSchedulesByConcertId(anyLong());
	}

	@Test
	public void 권한이_존재하는_유저_공연_좌석_전체_조회() {
		// given
		Long givenUserId = 1L;
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		List<ConcertSeat> givenConcertSeats = List.of(
			new ConcertSeat(1L, givenConcert, givenConcertSchedule, 1, 1000, false, LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSeat(2L, givenConcert, givenConcertSchedule, 2, 1000, false, LocalDateTime.now(), LocalDateTime.now()),
			new ConcertSeat(3L, givenConcert, givenConcertSchedule, 3, 1000, false, LocalDateTime.now(), LocalDateTime.now())
		);

		when(tokenRepository.hasActiveToken(anyString())).thenReturn(true);
		when(concertSeatRepository.findAllByConcertScheduleId(anyLong())).thenReturn(givenConcertSeats);

		// when
		List<ConcertSeat> concertSeats = concertService.scanAllSeats(givenUserId, givenConcertSchedule.getId());

		// then
		assertEquals(3, concertSeats.size());

		for (int i = 0; i < concertSeats.size(); i++) {
			ConcertSeat givenConcertSeat = givenConcertSeats.get(i);
			ConcertSeat concertSeat = concertSeats.get(i);

			assertEquals(concertSeat.getId(), givenConcertSeat.getId());
			assertEquals(concertSeat.getConcert(), givenConcertSeat.getConcert());
			assertEquals(concertSeat.getConcertSchedule(), givenConcertSeat.getConcertSchedule());
			assertEquals(concertSeat.getNumber(), givenConcertSeat.getNumber());
			assertEquals(concertSeat.getPrice(), givenConcertSeat.getPrice());
			assertEquals(concertSeat.getPaid(), givenConcertSeat.getPaid());
			assertEquals(concertSeat.getCreatedDate(), givenConcertSeat.getCreatedDate());
			assertEquals(concertSeat.getUpdatedDate(), givenConcertSeat.getUpdatedDate());
		}
	}

	@Test
	public void 권한이_없는_유저_좌석_전체_조회() {
		// given
		Long givenUserId = 1L;

		when(tokenRepository.hasActiveToken(anyString())).thenReturn(false);

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.scanAllSeats(givenUserId, 1L);
		});

		assertEquals("UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE", exception.getMessage());

		// then
		verify(concertSeatRepository, never()).findAllByConcertScheduleId(anyLong());
	}

	@Test
	public void 공연_좌석을_예약합니다() {
		// given
		Long givenUserId = 1L;
		Long givenSeatId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(givenSeatId, givenConcert, givenConcertSchedule, 1, 1000, false, LocalDateTime.now(),
			LocalDateTime.now());
		ConcertBooking givenConcertBooking = new ConcertBooking(givenUser, givenSeat);

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(concertSeatRepository.findByIdWithLock(anyLong())).thenReturn(givenSeat);
		when(concertBookingRepository.findBookedSeatBySeatId(any())).thenReturn(Optional.empty());
		when(concertBookingRepository.save(any())).thenReturn(givenConcertBooking);

		// when
		ConcertBooking concertBooking = concertService.bookConcertSeat(givenUserId, givenSeatId);

		// then
		assertEquals(concertBooking.getId(), givenConcertBooking.getId());
		assertEquals(concertBooking.getConcert(), givenConcertBooking.getConcert());
		assertEquals(concertBooking.getConcertSchedule(), givenConcertBooking.getConcertSchedule());
		assertEquals(concertBooking.getConcertSeat(), givenConcertBooking.getConcertSeat());
		assertEquals(concertBooking.getPrice(), givenConcertBooking.getPrice());
		assertEquals(concertBooking.getPaid(), givenConcertBooking.getPaid());
	}

	@Test
	public void 존재하지_않는_공연_좌석을_예약합니다() {
		// given
		Long givenUserId = 1L;
		Long givenSeatId = 1L;

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.bookConcertSeat(givenUserId, givenSeatId);
		});

		assertEquals("USER_NOT_FOUND", exception.getMessage());

		// then
		verify(concertSeatRepository, never()).findAllByConcertScheduleId(anyLong());
		verify(concertSeatRepository, never()).findByIdWithLock(anyLong());
		verify(concertBookingRepository, never()).findBookedSeatBySeatId(anyLong());
		verify(concertBookingRepository, never()).save(any());
	}

	@Test
	public void 이미_판매된_좌석을_예약합니다() {
		// given
		Long givenUserId = 1L;
		Long givenSeatId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(givenSeatId, givenConcert, givenConcertSchedule, 1, 1000, true, LocalDateTime.now(),
			LocalDateTime.now());

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(concertSeatRepository.findByIdWithLock(anyLong())).thenReturn(givenSeat);

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.bookConcertSeat(givenUserId, givenSeatId);
		});

		assertEquals("ALREADY_PAID_SEAT", exception.getMessage());

		// then
		verify(concertBookingRepository, never()).findBookedSeatBySeatId(anyLong());
		verify(concertBookingRepository, never()).save(any());
	}

	@Test
	public void 이미_예약된_좌석을_예약합니다() {
		// given
		Long givenUserId = 1L;
		Long givenSeatId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(givenSeatId, givenConcert, givenConcertSchedule, 1, 1000, false, LocalDateTime.now(),
			LocalDateTime.now());
		ConcertBooking givenConcertBooking = new ConcertBooking(1L, givenUser, givenConcert, givenConcertSchedule,givenSeat, 1000, false, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()  );

		when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenUser));
		when(concertSeatRepository.findByIdWithLock(anyLong())).thenReturn(givenSeat);
		when(concertBookingRepository.findBookedSeatBySeatId(any())).thenReturn(Optional.of(givenConcertBooking));

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.bookConcertSeat(givenUserId, givenSeatId);
		});

		assertEquals("ALREADY_BOOKED_SEAT", exception.getMessage());

		// then
		verify(concertBookingRepository, never()).save(any());
	}

	@Test
	public void 결제를_요청_합니다() {
		// given
		Long givenUserId = 1L;
		Long givenBookingId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 1000L, LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(1L, givenConcert, givenConcertSchedule, 1, 500, false, LocalDateTime.now(),
			LocalDateTime.now());
		ConcertBooking givenConcertBooking = new ConcertBooking(
			givenBookingId,
			givenUser,
			givenConcert,
			givenConcertSchedule,
			givenSeat,
			500,
			false,
			LocalDateTime.now().plusMinutes(5),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
		ConcertPayment givenConcertPayment = new ConcertPayment(givenConcertBooking);

		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenPoint));
		when(concertBookingRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(givenConcertBooking));
		when(concertPaymentRepository.save(any())).thenReturn(givenConcertPayment);

		// when
		ConcertPayment concertBooking = concertService.pay(givenUserId, givenBookingId);

		// then
		assertEquals(concertBooking.getId(), givenConcertPayment.getId());
		assertEquals(concertBooking.getConcert(), givenConcertPayment.getConcert());
		assertEquals(concertBooking.getConcertSchedule(), givenConcertPayment.getConcertSchedule());
		assertEquals(concertBooking.getConcertSeat(), givenConcertPayment.getConcertSeat());
		assertEquals(concertBooking.getConcertBooking(), givenConcertPayment.getConcertBooking());
		assertEquals(concertBooking.getPrice(), givenConcertPayment.getPrice());
	}

	@Test
	public void 포인트가_존재하지_않는_유저가_결제를_요청_합니다() {
		// given
		Long givenUserId = 1L;
		Long givenBookingId = 1L;

		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.pay(givenUserId, givenBookingId);
		});

		assertEquals("POINT_NOT_FOUND", exception.getMessage());

		// then
		verify(concertBookingRepository, never()).findByIdAndUserId(anyLong(), anyLong());
		verify(concertPaymentRepository, never()).save(any());
	}

	@Test
	public void 예약이_존재하지_않는_유저가_결제를_요청_합니다() {
		// given
		Long givenUserId = 1L;
		Long givenBookingId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 1000L, LocalDateTime.now(), LocalDateTime.now());

		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenPoint));
		when(concertBookingRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.pay(givenUserId, givenBookingId);
		});

		assertEquals("NOT_FOUND_CONCERT_BOOKING", exception.getMessage());

		// then
		verify(concertPaymentRepository, never()).save(any());
	}

	@Test
	public void 포인트가_부족한_유저가_결제를_요청_합니다() {
		// given
		Long givenUserId = 1L;
		Long givenBookingId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 400L, LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(1L, givenConcert, givenConcertSchedule, 1, 500, false, LocalDateTime.now(),
			LocalDateTime.now());
		ConcertBooking givenConcertBooking = new ConcertBooking(
			givenBookingId,
			givenUser,
			givenConcert,
			givenConcertSchedule,
			givenSeat,
			500,
			false,
			LocalDateTime.now().plusMinutes(5),
			LocalDateTime.now(),
			LocalDateTime.now()
		);

		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenPoint));
		when(concertBookingRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(givenConcertBooking));

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.pay(givenUserId, givenBookingId);
		});

		assertEquals("INSUFFICIENT_POINTS", exception.getMessage());

		// then
		verify(concertPaymentRepository, never()).save(any());
	}

	@Test
	public void 만료된_예약을_유저가_결제를_요청_합니다() {
		// given
		Long givenUserId = 1L;
		Long givenBookingId = 1L;
		User givenUser = new User(givenUserId, "테스트", LocalDateTime.now(), LocalDateTime.now());
		Point givenPoint = new Point(1L, givenUser, 600L, LocalDateTime.now(), LocalDateTime.now());
		Concert givenConcert = new Concert(1L, "테스트_제목", "테스트_설명", LocalDateTime.now(), LocalDateTime.now());
		ConcertSchedule givenConcertSchedule =
			new ConcertSchedule(1L, givenConcert, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now());
		ConcertSeat givenSeat = new ConcertSeat(1L, givenConcert, givenConcertSchedule, 1, 500, false, LocalDateTime.now(),
			LocalDateTime.now());
		ConcertBooking givenConcertBooking = new ConcertBooking(
			givenBookingId,
			givenUser,
			givenConcert,
			givenConcertSchedule,
			givenSeat,
			500,
			false,
			LocalDateTime.now().minusMinutes(5),
			LocalDateTime.now(),
			LocalDateTime.now()
		);

		when(pointRepository.findByUserId(anyLong())).thenReturn(Optional.of(givenPoint));
		when(concertBookingRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(givenConcertBooking));

		// when
		CoreException exception = assertThrows(CoreException.class, () -> {
			concertService.pay(givenUserId, givenBookingId);
		});

		assertEquals("RESERVATION_EXPIRED", exception.getMessage());

		// then
		verify(concertPaymentRepository, never()).save(any());
	}
}