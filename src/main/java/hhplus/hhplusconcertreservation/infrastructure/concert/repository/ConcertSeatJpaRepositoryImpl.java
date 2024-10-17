package hhplus.hhplusconcertreservation.infrastructure.concert.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertSeatRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.mapper.ConcertSeatMapper;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertSeatJpaRepositoryImpl implements ConcertSeatRepository {
	private final ConcertSeatJpaRepository concertSeatJpaRepository;

	@Override
	public List<ConcertSeat> findAllByConcertScheduleId(Long concertScheduleId) {
		return concertSeatJpaRepository.findAllByConcertScheduleId(concertScheduleId)
			.stream().map(ConcertSeatMapper::toDomain).toList();
	}
}
