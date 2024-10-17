package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
}
