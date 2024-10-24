package hhplus.hhplusconcertreservation.domain.concert.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
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
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {
	private final TokenService tokenService;
	private final ConcertScheduleRepository concertScheduleRepository;
	private final ConcertSeatRepository concertSeatRepository;
	private final ConcertBookingRepository concertBookingRepository;
	private final ConcertPaymentRepository concertPaymentRepository;
	private final UserQueueRepository userQueueRepository;
	private final UserRepository userRepository;
	private final PointRepository pointRepository;

	@Transactional
	public ConcertPayment pay(Long userId, Long bookingId) {
		Point point = pointRepository.findByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.POINT_NOT_FOUND, Map.of("userId", userId)));
		ConcertBooking concertBooking = concertBookingRepository.findByIdAndUserId(bookingId, userId)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND_CONCERT_BOOKING, Map.of("bookingId", bookingId, "userId", userId)));

		point.validateSufficientPoints(concertBooking.getPrice());
		concertBooking.validateReservationExpiration();

		point.deduct(concertBooking.getPrice());
		pointRepository.update(point);

		ConcertSeat concertSeat = concertBooking.getConcertSeat();
		concertSeat.outBox();
		concertSeatRepository.save(concertSeat);

		concertBooking.outBox();
		concertBookingRepository.save(concertBooking);

		return concertPaymentRepository.save(new ConcertPayment(concertBooking));
	}

	@Transactional
	public ConcertBooking bookConcertSeat(Long userId, Long seatId) {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));

		ConcertSeat seat = concertSeatRepository.findByIdWithLock(seatId);
		seat.validateSeatPayment();

		Optional<ConcertBooking> alreadyBooked = concertBookingRepository.findBookedSeatBySeatId(seatId);
		if(alreadyBooked.isPresent()) {
			throw new CoreException(ErrorType.ALREADY_BOOKED_SEAT, Map.of("seatId", seatId, "bookingId", alreadyBooked.get().getId()));
		}

		ConcertBooking concertBooking = new ConcertBooking(user, seat);
		concertBookingRepository.save(concertBooking);

		return concertBooking;
	}

	public List<ConcertSchedule> scanAllBookableConcertSchedules(Long userId, Long concertId) {
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(() ->
			new CoreException(ErrorType.UNABLE_TO_RETRIEVE_CONCERT_SCHEDULE, Map.of("userId", userId))
		);

		return concertScheduleRepository.findAllBookableSchedulesByConcertId(concertId);
	}

	public List<ConcertSeat> scanAllSeats(Long userId, Long concertScheduleId) {
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.UNABLE_TO_RETRIEVE_CONCERT_SEAT, Map.of("userId", userId)));

		return concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);
	}
}
