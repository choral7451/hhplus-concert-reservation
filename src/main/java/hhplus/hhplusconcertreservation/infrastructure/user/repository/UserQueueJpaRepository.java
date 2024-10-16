package hhplus.hhplusconcertreservation.infrastructure.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;

public interface UserQueueJpaRepository extends JpaRepository<UserQueueEntity, Long> {
}
