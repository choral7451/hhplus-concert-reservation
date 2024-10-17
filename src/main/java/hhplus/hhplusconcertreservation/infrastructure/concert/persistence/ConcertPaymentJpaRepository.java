package hhplus.hhplusconcertreservation.infrastructure.concert.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertPaymentEntity;

public interface ConcertPaymentJpaRepository extends JpaRepository<ConcertPaymentEntity, Long> {
}
