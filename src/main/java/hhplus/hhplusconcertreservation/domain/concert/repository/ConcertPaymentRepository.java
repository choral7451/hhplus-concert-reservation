package hhplus.hhplusconcertreservation.domain.concert.repository;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;

public interface ConcertPaymentRepository {
	ConcertPayment save(ConcertPayment concertPayment);
}
