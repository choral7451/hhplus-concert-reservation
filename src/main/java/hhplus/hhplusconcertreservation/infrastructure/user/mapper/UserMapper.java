package hhplus.hhplusconcertreservation.infrastructure.user.mapper;

import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;

public class UserMapper {
	static public User toDomain(UserEntity entity) {
		return new User(entity.getId(), entity.getName(), entity.getCreatedDate(), entity.getUpdatedDate());
	}

	static public UserEntity toEntity(User domain) {
		return UserEntity.builder()
			.id(domain.getId())
			.name(domain.getName())
			.createdDate(domain.getCreatedDate())
			.updatedDate(domain.getUpdatedDate())
			.build();
	}
}
