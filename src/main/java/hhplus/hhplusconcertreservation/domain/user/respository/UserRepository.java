package hhplus.hhplusconcertreservation.domain.user.respository;

import java.util.Optional;

import hhplus.hhplusconcertreservation.domain.user.model.User;

public interface UserRepository {
	Optional<User> findByUserId(Long userId);
}
