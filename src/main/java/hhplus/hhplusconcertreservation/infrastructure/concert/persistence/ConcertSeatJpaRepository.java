package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
	List<ConcertSeatEntity> findAllByConcertScheduleId(Long concertScheduleId);
}
