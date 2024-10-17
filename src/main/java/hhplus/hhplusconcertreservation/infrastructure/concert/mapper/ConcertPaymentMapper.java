package hhplus.hhplusconcertreservation.infrastructure.concert.mapper;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertPayment;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertPaymentEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserMapper;

public class ConcertPaymentMapper {
	static public ConcertPayment toDomain(ConcertPaymentEntity entity) {

		return new ConcertPayment(
			entity.getId(),
			UserMapper.toDomain(entity.getUser()),
			ConcertMapper.toDomain(entity.getConcert()),
			ConcertScheduleMapper.toDomain(entity.getConcertSchedule()),
			ConcertSeatMapper.toDomain(entity.getConcertSeat()),
			ConcertBookingMapper.toDomain(entity.getConcertBooking()),
			entity.getPrice(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public ConcertPaymentEntity toEntity(ConcertPayment domain) {
		return ConcertPaymentEntity.builder()
			.id(domain.getId())
			.user(UserMapper.toEntity(domain.getUser()))
			.concert(ConcertMapper.toEntity(domain.getConcert()))
			.concertSchedule(ConcertScheduleMapper.toEntity(domain.getConcertSchedule()))
			.concertSeat(ConcertSeatMapper.toEntity(domain.getConcertSeat()))
			.price(domain.getPrice())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
