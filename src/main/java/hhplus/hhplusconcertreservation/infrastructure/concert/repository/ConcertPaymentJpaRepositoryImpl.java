package hhplus.hhplusconcertreservation.infrastructure.concert.repository;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertPaymentRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertPaymentEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.mapper.ConcertPaymentMapper;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertPaymentJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertPaymentJpaRepositoryImpl implements ConcertPaymentRepository {
	private final ConcertPaymentJpaRepository concertPaymentJpaRepository;

	@Override
	public ConcertPayment save(ConcertPayment concertPayment) {
		ConcertPaymentEntity entity = this.concertPaymentJpaRepository.save(ConcertPaymentMapper.toEntity(concertPayment));
		return ConcertPaymentMapper.toDomain(entity);
	}
}
