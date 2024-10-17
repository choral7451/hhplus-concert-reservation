package hhplus.hhplusconcertreservation.domain.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.concert.exception.UnableToRetrieveConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.exception.UnableToRetrieveConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertScheduleRepository;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertSeatRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {
	private final TokenService tokenService;
	private final ConcertScheduleRepository concertScheduleRepository;
	private final UserQueueRepository userQueueRepository;
	private final ConcertSeatRepository concertSeatRepository;

	public List<ConcertSchedule> scanAllBookableConcertSchedules(String token, Long concertId) {
		Long userId = tokenService.getUserId(token);
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(UnableToRetrieveConcertSchedule::new);

		return concertScheduleRepository.findAllBookableSchedulesByConcertId(concertId);
	}

	public List<ConcertSeat> scanAllSeats(String token, Long concertScheduleId) {
		Long userId = tokenService.getUserId(token);
		userQueueRepository.findActiveUserQueueByUserId(userId).orElseThrow(UnableToRetrieveConcertSeat::new);

		return concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);
	}
}
