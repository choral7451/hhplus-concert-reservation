package hhplus.hhplusconcertreservation.infrastructure.concert.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import hhplus.hhplusconcertreservation.domain.concert.repository.ConcertBookingRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertBookingEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.mapper.ConcertBookingMapper;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertBookingJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertBookingJpaRepositoryImpl implements ConcertBookingRepository {

	private final ConcertBookingJpaRepository concertBookingJpaRepository;

	@Override
	public ConcertBooking save(ConcertBooking concertBooking) {
		ConcertBookingEntity entity = this.concertBookingJpaRepository.save(ConcertBookingMapper.toEntity(concertBooking));
		return ConcertBookingMapper.toDomain(entity);
	}

	@Override
	public Optional<ConcertBooking> findBookedSeatBySeatId(Long seatId) {
		return this.concertBookingJpaRepository.findBookedSeatBySeatId(seatId)
			.map(ConcertBookingMapper::toDomain);
	}

	@Override
	public Optional<ConcertBooking> findByIdAndUserId(Long bookingId, Long userId) {
		return this.concertBookingJpaRepository.findByIdAndUserId(bookingId, userId)
			.map(ConcertBookingMapper::toDomain);
	}
}
