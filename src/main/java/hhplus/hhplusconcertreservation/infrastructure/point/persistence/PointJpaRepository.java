package hhplus.hhplusconcertreservation.infrastructure.point.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {
	@Query("SELECT p FROM PointEntity p WHERE p.user.id = :userId")
	Optional<PointEntity> findByUserId(Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM PointEntity p WHERE p.user.id = :userId")
	Optional<PointEntity> findByUserIdWithLock(@Param("userId") Long userId);
}