package hhplus.hhplusconcertreservation.infrastructure.user.repository;


import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserQueueMapper;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserQueueJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueueJpaRepositoryImpl implements UserQueueRepository {
	private final UserQueueJpaRepository userQueueJpaRepository;

	@Override
	public int countCurrentOrderByUserId(Long userId) {
		return userQueueJpaRepository.countCurrentOrderByUserId(userId);
	}

	@Override
	public Optional<UserQueue> findByUserId(Long userId) {
		Optional<UserQueueEntity> userQueueEntity = userQueueJpaRepository.findByUserId(userId);
		return userQueueEntity.map(UserQueueMapper::toDomain);
	}

	@Override
	public UserQueue save(Long userId, String token) {
		UserQueue userQueue = new UserQueue(userId, token);
		UserQueueEntity userQueueEntity = userQueueJpaRepository.save(UserQueueMapper.toEntity(userQueue));
		return UserQueueMapper.toDomain(userQueueEntity);
	}
}
