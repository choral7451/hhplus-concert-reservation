package hhplus.hhplusconcertreservation.infrastructure.point.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.point.repository.PointRepository;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;
import hhplus.hhplusconcertreservation.infrastructure.point.mapper.PointMapper;
import hhplus.hhplusconcertreservation.infrastructure.point.persistence.PointJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointJpaRepositoryImpl implements PointRepository {
	private final PointJpaRepository pointJpaRepository;

	@Override
	public Optional<Point> findByUserId(Long userId) {
		Optional<PointEntity> pointEntity = pointJpaRepository.findByUserId(userId);
		return pointEntity.map(PointMapper::toDomain);
	}

	@Override
	public Point save(User user) {
		PointEntity entity = PointEntity.builder()
			.user(UserMapper.toEntity(user))
			.amount(0L)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		pointJpaRepository.save(entity);

		return PointMapper.toDomain(entity);
	}

	@Override
	public Point update(Point point) {
		PointEntity entity = pointJpaRepository.save(PointMapper.toEntity(point));
		return PointMapper.toDomain(entity);
	}
}
