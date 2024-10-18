package hhplus.hhplusconcertreservation.infrastructure.concert.mapper;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertBookingEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserMapper;

public class ConcertBookingMapper {
	static public ConcertBooking toDomain(ConcertBookingEntity entity) {
		return new ConcertBooking(
			entity.getId(),
			UserMapper.toDomain(entity.getUser()),
			ConcertMapper.toDomain(entity.getConcert()),
			ConcertScheduleMapper.toDomain(entity.getConcertSchedule()),
			ConcertSeatMapper.toDomain(entity.getConcertSeat()),
			entity.getPrice(),
			entity.getIsPaid(),
			entity.getExpiresDate(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public ConcertBookingEntity toEntity(ConcertBooking domain) {
		return ConcertBookingEntity.builder()
			.id(domain.getId())
			.user(UserMapper.toEntity(domain.getUser()))
			.concert(ConcertMapper.toEntity(domain.getConcert()))
			.concertSchedule(ConcertScheduleMapper.toEntity(domain.getConcertSchedule()))
			.concertSeat(ConcertSeatMapper.toEntity(domain.getConcertSeat()))
			.price(domain.getPrice())
			.isPaid(domain.getPaid())
			.expiresDate(domain.getExpiresDate())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
