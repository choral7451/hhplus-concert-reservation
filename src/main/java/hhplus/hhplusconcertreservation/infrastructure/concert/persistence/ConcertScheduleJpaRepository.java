package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertScheduleEntity;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {
	@Query(value = "SELECT cs FROM ConcertScheduleEntity cs WHERE cs.concert.id = :concertId " +
		"AND cs.bookingStartDate <= CURRENT_TIMESTAMP " +
		"AND cs.bookingEndDate >= CURRENT_TIMESTAMP")
	List<ConcertScheduleEntity> findAllBookableSchedulesByConcertId(@Param("concertId") Long concertId);
}
