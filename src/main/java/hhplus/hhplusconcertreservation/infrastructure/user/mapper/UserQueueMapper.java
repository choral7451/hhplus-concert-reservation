package hhplus.hhplusconcertreservation.infrastructure.user.mapper;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;

public class UserQueueMapper {
	static public UserQueue toDomain(UserQueueEntity entity) {
		return new UserQueue(
			entity.getId(),
			entity.getUserId(),
			entity.getToken(),
			entity.getExpiresDate(),
			entity.getCreatedDate(),
			entity.getUpdatedDate()
		);
	}

	static public UserQueueEntity toEntity(UserQueue domain) {
		return UserQueueEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.token(domain.getToken())
			.expiresDate(domain.getExpiresDate())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
