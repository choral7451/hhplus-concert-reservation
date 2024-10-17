package hhplus.hhplusconcertreservation.infrastructure.concert.mapper;

import hhplus.hhplusconcertreservation.domain.concert.model.Concert;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertEntity;

public class ConcertMapper {
	static public Concert toDomain(ConcertEntity entity) {
		return new Concert(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getCreatedDate(),
			entity.getUpdatedDate());
	}

	static public ConcertEntity toEntity(Concert domain) {
		return ConcertEntity.builder()
			.id(domain.getId())
			.title(domain.getTitle())
			.description(domain.getDescription())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}

