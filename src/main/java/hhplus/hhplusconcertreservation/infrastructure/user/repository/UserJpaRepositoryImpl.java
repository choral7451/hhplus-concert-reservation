package hhplus.hhplusconcertreservation.infrastructure.user.repository;


import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserMapper;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findByUserId(Long userId) {
		return userJpaRepository.findById(userId)
			.map(UserMapper::toDomain);
	}
}
