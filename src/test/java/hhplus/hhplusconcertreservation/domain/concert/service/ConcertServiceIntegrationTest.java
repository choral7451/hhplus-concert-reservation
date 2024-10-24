package hhplus.hhplusconcertreservation.domain.concert.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import hhplus.hhplusconcertreservation.common.DatabaseCleanUp;
import hhplus.hhplusconcertreservation.domain.concert.model.ConcertBooking;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertScheduleEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertScheduleJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertSeatJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserQueueEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.enums.UserQueueEntityStatus;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserQueueJpaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
public class ConcertServiceIntegrationTest {
	@Value("${variables.waitingTokenSecretKey}")
	private String waitingTokenSecretKey;

	@Autowired
	private ConcertService concertService;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private UserQueueJpaRepository userQueueJpaRepository;

	@Autowired
	private ConcertSeatJpaRepository concertSeatJpaRepository;

	@Autowired
	private ConcertJpaRepository concertJpaRepository;

	@Autowired
	private ConcertScheduleJpaRepository concertScheduleJpaRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	public void cleanup() {
		databaseCleanUp.execute();
	}
	@Test
	public void 한좌석은_한명만_예약할_수_있습니다() throws Exception {
		// given
		Long givenUserId = 1L;

		SecretKey key = Keys.hmacShaKeyFor(waitingTokenSecretKey.getBytes());

		String givenWaitingToken = Jwts.builder()
			.claim("userId", givenUserId)
			.signWith(key)
			.compact();

		UserEntity user = UserEntity.builder()
			.id(givenUserId)
			.name("테스트")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		userJpaRepository.save(user);

		UserQueueEntity userQueue = UserQueueEntity.builder()
			.userId(givenUserId)
			.status(UserQueueEntityStatus.WAITING)
			.token(givenWaitingToken)
			.expiresDate(LocalDateTime.now().plusMinutes(5))
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		userQueueJpaRepository.save(userQueue);

		ConcertEntity concert = ConcertEntity.builder()
			.title("테스트")
			.description("테스트")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		concertJpaRepository.save(concert);

		ConcertScheduleEntity concertSchedule = ConcertScheduleEntity.builder()
			.concert(concert)
			.bookingStartDate(LocalDateTime.now().minusDays(1))
			.bookingEndDate(LocalDateTime.now().plusDays(1))
			.performanceDate(LocalDateTime.now())
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		concertScheduleJpaRepository.save(concertSchedule);

		ConcertSeatEntity seat = ConcertSeatEntity.builder()
			.concert(concert)
			.isPaid(false)
			.number(1)
			.price(1000)
			.concertSchedule(concertSchedule)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now()).build();
		concertSeatJpaRepository.save(seat);

		int threadCount = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		List<Callable<ConcertBooking>> callables = new ArrayList<>();

		for (int i = 0; i < threadCount; i++) {
			callables.add(() -> {
				return concertService.bookConcertSeat(givenUserId, seat.getId());
			});
		}


		List<Future<ConcertBooking>> futures = executorService.invokeAll(callables);

		int successCount = 0;
		int errorCount = 0;

		for (Future<ConcertBooking> future : futures) {
			try {
				ConcertBooking result = future.get();
				successCount++;
			} catch (ExecutionException e) {
				errorCount++;
				System.err.println("Error occurred: " + e.getCause().getMessage());
			}
		}

		assertEquals(1, successCount, "성공한 요청의 수는 1이어야 합니다.");
		assertEquals(4, errorCount, "실패한 요청의 수는 4이어야 합니다.");

		executorService.shutdown();
	}
}
