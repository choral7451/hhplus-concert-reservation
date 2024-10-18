package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;
import jakarta.persistence.LockModeType;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
	List<ConcertSeatEntity> findAllByConcertScheduleId(Long concertScheduleId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM ConcertSeatEntity c WHERE c.id = :id")
	ConcertSeatEntity findByIdWithLock(@Param("id") Long id);
}
