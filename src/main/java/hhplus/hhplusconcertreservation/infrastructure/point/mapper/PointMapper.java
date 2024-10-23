package hhplus.hhplusconcertreservation.infrastructure.point.mapper;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserMapper;

public class PointMapper {
	static public Point toDomain(PointEntity entity) {
		return new Point(
			entity.getId(),
			UserMapper.toDomain(entity.getUser()),
			entity.getAmount(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public PointEntity toEntity(Point domain) {
		return PointEntity.builder()
			.id(domain.getId())
			.user(UserMapper.toEntity(domain.getUser()))
			.amount(domain.getAmount())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
