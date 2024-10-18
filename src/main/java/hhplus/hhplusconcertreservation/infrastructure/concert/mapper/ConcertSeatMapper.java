package hhplus.hhplusconcertreservation.infrastructure.concert.mapper;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSeat;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;

public class ConcertSeatMapper {
	static public ConcertSeat toDomain(ConcertSeatEntity entity) {
		return new ConcertSeat(
			entity.getId(),
			ConcertMapper.toDomain(entity.getConcert()),
			ConcertScheduleMapper.toDomain(entity.getConcertSchedule()),
			entity.getNumber(),
			entity.getPrice(),
			entity.getIsPaid(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public ConcertSeatEntity toEntity(ConcertSeat domain) {
		return ConcertSeatEntity.builder()
			.id(domain.getId())
			.concert(ConcertMapper.toEntity(domain.getConcert()))
			.concertSchedule(ConcertScheduleMapper.toEntity(domain.getConcertSchedule()))
			.number(domain.getNumber())
			.price(domain.getPrice())
			.isPaid(domain.getPaid())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
