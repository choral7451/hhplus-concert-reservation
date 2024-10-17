package hhplus.hhplusconcertreservation.domain.user.respository;

import java.util.Optional;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;

public interface UserQueueRepository {
	UserQueue save(Long userId, String token);
	Optional<UserQueue> findByUserId(Long userId);
	int countCurrentOrderByUserId(Long userId);
}
