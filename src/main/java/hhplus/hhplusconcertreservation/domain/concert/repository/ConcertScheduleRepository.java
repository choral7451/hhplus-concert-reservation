package hhplus.hhplusconcertreservation.domain.concert.repository;

import java.util.List;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;

public interface ConcertScheduleRepository {
	List<ConcertSchedule> findAllBookableSchedulesByConcertId(Long concertId);
}
