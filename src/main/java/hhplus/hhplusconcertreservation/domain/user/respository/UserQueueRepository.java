package hhplus.hhplusconcertreservation.domain.user.respository;

import java.time.LocalDateTime;
import java.util.Optional;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;

public interface UserQueueRepository {
	UserQueue save(Long userId, String token);
	Optional<UserQueue> findByUserId(Long userId);
	Optional<UserQueue> findActiveUserQueueByUserId(Long userId);
	int countCurrentOrderByUserId(Long userId);
	void deleteExpiredUserQueues();
	void activateUserQueues(int limit, LocalDateTime expiresDate);
	int countActiveUserQueues();
}
