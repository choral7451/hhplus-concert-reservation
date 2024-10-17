package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertBookingEntity;

public interface ConcertBookingJpaRepository extends JpaRepository<ConcertBookingEntity, Long> {
	@Query("SELECT c FROM ConcertBookingEntity c WHERE c.concertSeat.id = :seatId AND c.isPaid = false AND c.expiresDate >= CURRENT_TIMESTAMP")
	Optional<ConcertBookingEntity> findBookedSeatBySeatId(@Param("seatId") Long seatId);
	Optional<ConcertBookingEntity> findByIdAndUserId(Long id, Long userId);
}
