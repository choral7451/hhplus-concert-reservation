package hhplus.hhplusconcertreservation.infrastructure.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;

public interface UserQueueJpaRepository extends JpaRepository<UserQueueEntity, Long> {
	Optional<UserQueueEntity> findByUserId(Long userId);
	@Query("SELECT COUNT(u) FROM UserQueueEntity u WHERE u.status = 'WAITING' AND u.id <= (SELECT u2.id FROM UserQueueEntity u2 WHERE u2.userId = :userId)")
	int countCurrentOrderByUserId(@Param("userId") Long userId);

	@Query("SELECT u FROM UserQueueEntity u WHERE u.userId = :userId AND u.status = 'ACTIVATION' AND u.expiresDate >= CURRENT_TIMESTAMP")
	Optional<UserQueueEntity> findActiveUserQueueByUserId(@Param("userId") Long userId);
}
