package hhplus.hhplusconcertreservation.infrastructure.concert.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertScheduleRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.mapper.ConcertScheduleMapper;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertScheduleJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleJpaRepositoryImpl implements ConcertScheduleRepository {
	private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

	@Override
	public List<ConcertSchedule> findAllBookableSchedulesByConcertId(Long concertId) {
		return concertScheduleJpaRepository.findAllBookableSchedulesByConcertId(concertId)
			.stream().map(ConcertScheduleMapper::toDomain).toList();
	}
}
