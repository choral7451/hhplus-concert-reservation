package hhplus.hhplusconcertreservation.infrastructure.user.repository;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.repository.UserQueueRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserQueueMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UseQueueJpaRepositoryImpl implements UserQueueRepository {

	private final UserQueueJpaRepository userQueueJpaRepository;

	@Override
	public UserQueue save(UserQueue userQueue) {
		UserQueueEntity entity = UserQueueMapper.toEntity(userQueue);
		userQueueJpaRepository.save(entity);

		return UserQueueMapper.toDomain(entity);
	}
}
