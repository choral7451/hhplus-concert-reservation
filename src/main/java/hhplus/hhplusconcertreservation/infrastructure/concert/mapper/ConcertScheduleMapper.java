package hhplus.hhplusconcertreservation.infrastructure.concert.mapper;

import hhplus.hhplusconcertreservation.domain.concert.model.ConcertSchedule;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertScheduleEntity;

public class ConcertScheduleMapper {
	static public ConcertSchedule toDomain(ConcertScheduleEntity entity) {
		return new ConcertSchedule(
			entity.getId(),
			ConcertMapper.toDomain(entity.getConcert()),
			entity.getBookingStartDate(),
			entity.getBookingEndDate(),
			entity.getPerformanceDate(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public ConcertScheduleEntity toEntity(ConcertSchedule domain) {
		return ConcertScheduleEntity.builder()
			.id(domain.getId())
			.concert(ConcertMapper.toEntity(domain.getConcert()))
			.bookingStartDate(domain.getBookingStartDate())
			.bookingEndDate(domain.getBookingEndDate())
			.performanceDate(domain.getPerformanceDate())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
