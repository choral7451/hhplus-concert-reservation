package hhplus.hhplusconcertreservation.infrastructure.user.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;
import jakarta.transaction.Transactional;

public interface UserQueueJpaRepository extends JpaRepository<UserQueueEntity, Long> {
	Optional<UserQueueEntity> findByUserId(Long userId);
	@Query("SELECT COUNT(u) FROM UserQueueEntity u WHERE u.status = 'WAITING' AND u.id <= (SELECT u2.id FROM UserQueueEntity u2 WHERE u2.userId = :userId)")
	int countCurrentOrderByUserId(@Param("userId") Long userId);

	@Query("SELECT u FROM UserQueueEntity u WHERE u.userId = :userId AND u.status = 'ACTIVATION' AND u.expiresDate >= CURRENT_TIMESTAMP")
	Optional<UserQueueEntity> findActiveUserQueueByUserId(@Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query("UPDATE UserQueueEntity u SET u.deletedDate = CURRENT_TIMESTAMP WHERE u.expiresDate < CURRENT_TIMESTAMP AND u.deletedDate IS NULL")
	void deleteExpiredUserQueues();

	@Transactional
	@Modifying
	@Query(value = "UPDATE user_queues u SET u.status = 'ACTIVATION', u.expires_date = :expiresDate " +
		"WHERE u.id IN (SELECT u2.id FROM user_queues u2 WHERE u2.status = 'WAITING' " +
		"ORDER BY u2.id ASC LIMIT :limit)", nativeQuery = true)
	void activateUserQueues(@Param("limit") int limit, @Param("expiresDate") LocalDateTime expiresDate);

	@Query("SELECT COUNT(u) FROM UserQueueEntity u WHERE u.status = 'ACTIVATION'")
	int countActiveUserQueues();
}
