package hhplus.hhplusconcertreservation.interfaces.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hhplus.hhplusconcertreservation.common.DatabaseCleanUp;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.enums.UserQueueEntityStatus;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserQueueJpaRepository;

@SpringBootTest
class WaitingSchedulerIntegrationTest {
	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@Autowired
	private WaitingScheduler waitingScheduler;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private UserQueueJpaRepository userQueueJpaRepository;

	@AfterEach
	public void cleanup() {
		databaseCleanUp.execute();
	}

	@Test
	public void 대기열_최대_활성화_인원은_10명_입니다() throws Exception {
		// given
		for (long i = 1; i <= 11; i++) {
			UserEntity user = UserEntity.builder()
				.id(i)
				.name("테스트 " + i)
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
			userJpaRepository.save(user);


			UserQueueEntity userQueue = UserQueueEntity.builder()
				.userId(user.getId())
				.status(UserQueueEntityStatus.WAITING)
				.token(String.valueOf(i))
				.expiresDate(LocalDateTime.now().plusMinutes(5))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
			userQueueJpaRepository.save(userQueue);
		}

		// when
		waitingScheduler.activeUserQueuesScheduler();

		// then
		int activeUserQueueCount = userQueueJpaRepository.countActiveUserQueues();
		assertEquals(10, activeUserQueueCount);
	}

	@Test
	public void 만료된_대기열을_삭제합니다() throws Exception {
		// given
		UserEntity user = UserEntity.builder()
			.id(1L)
			.name("테스트")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		userJpaRepository.save(user);


		UserQueueEntity givenUserQueue = UserQueueEntity.builder()
			.id(1L)
			.userId(user.getId())
			.status(UserQueueEntityStatus.WAITING)
			.token(String.valueOf(1))
			.expiresDate(LocalDateTime.now().minusDays(1))
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		userQueueJpaRepository.save(givenUserQueue);

		// when
		waitingScheduler.deleteExpiredUserQueuesScheduler();

		// then
		Optional<UserQueueEntity> userQueue = userQueueJpaRepository.findById(1L);
		assertEquals(Optional.empty(), userQueue);
	}
}