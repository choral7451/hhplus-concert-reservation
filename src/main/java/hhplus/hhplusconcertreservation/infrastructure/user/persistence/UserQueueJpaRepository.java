package hhplus.hhplusconcertreservation.infrastructure.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;

public interface UserQueueJpaRepository extends JpaRepository<UserQueueEntity, Long> {
	Optional<UserQueueEntity> findByUserId(Long userId);
}
