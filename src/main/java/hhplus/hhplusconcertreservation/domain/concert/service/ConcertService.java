package hhplus.hhplusconcertreservation.domain.concert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.concert.exception.AlreadyBookedSeat;
import hhplus.hhplusconcertreservation.domain.concert.exception.AlreadyPaidSeat;
import hhplus.hhplusconcertreservation.domain.concert.exception.UnableToRetrieveConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.exception.UnableToRetrieveConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertBookingRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertScheduleRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertSeatRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {
	private final TokenService tokenService;
	private final ConcertScheduleRepository concertScheduleRepository;
	private final ConcertSeatRepository concertSeatRepository;
	private final ConcertBookingRepository concertBookingRepository;
	private final UserQueueRepository userQueueRepository;
	private final UserRepository userRepository;

	@Transactional
	public ConcertBooking bookConcertSeat(String token, Long seatId) {
		Long userId = tokenService.getUserIdByWaitingToken(token);
		User user = userRepository.findByUserId(userId).orElseThrow(UserNotFound::new);
		ConcertSeat seat = concertSeatRepository.findByIdWithLock(seatId);
		if(seat.getPaid()) throw new AlreadyPaidSeat();

		Optional<ConcertBooking> alreadyBooked = concertBookingRepository.findBookedSeatBySeatId(seatId);
		if(alreadyBooked.isPresent()) throw new AlreadyBookedSeat();

		ConcertBooking concertBooking = new ConcertBooking(user, seat);
		concertBookingRepository.save(concertBooking);

		return concertBooking;
	}

	public List<ConcertSchedule> scanAllBookableConcertSchedules(String token, Long concertId) {
		Long userId = tokenService.getUserIdByWaitingToken(token);
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(UnableToRetrieveConcertSchedule::new);

		return concertScheduleRepository.findAllBookableSchedulesByConcertId(concertId);
	}

	public List<ConcertSeat> scanAllSeats(String token, Long concertScheduleId) {
		Long userId = tokenService.getUserIdByWaitingToken(token);
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(UnableToRetrieveConcertSeat::new);

		return concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);
	}
}
