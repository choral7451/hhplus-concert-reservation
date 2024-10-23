package hhplus.hhplusconcertreservation.infrastructure.point.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;

public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {
	Optional<PointEntity> findByUserId(Long userId);
}
