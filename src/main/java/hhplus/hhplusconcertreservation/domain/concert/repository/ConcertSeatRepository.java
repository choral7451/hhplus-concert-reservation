package hhplus.hhplusconcertreservation.domain.concert.repository;

import java.util.List;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;

public interface ConcertSeatRepository {
	List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId);
	ConcertSeat findByIdWithLock(Long id);
}
