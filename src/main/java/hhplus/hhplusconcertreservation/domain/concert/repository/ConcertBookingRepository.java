package hhplus.hhplusconcertreservation.domain.concert.repository;

import java.util.Optional;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;

public interface ConcertBookingRepository {
	ConcertBooking save(ConcertBooking concertBooking);
	Optional<ConcertBooking> findBookedSeatBySeatId(Long seatId);
}
