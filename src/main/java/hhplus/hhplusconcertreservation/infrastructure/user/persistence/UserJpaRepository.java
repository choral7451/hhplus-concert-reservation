package hhplus.hhplusconcertreservation.infrastructure.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
