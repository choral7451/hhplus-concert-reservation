package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertEntity;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}
