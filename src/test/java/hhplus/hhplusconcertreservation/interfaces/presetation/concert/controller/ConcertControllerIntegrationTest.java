package hhplus.hhplusconcertreservation.interfaces.presetation.concert.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import hhplus.hhplusconcertreservation.common.DatabaseCleanUp;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertBookingEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertScheduleEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.entity.ConcertSeatEntity;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertBookingJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertScheduleJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.concert.persistence.ConcertSeatJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;
import hhplus.hhplusconcertreservation.infrastructure.point.persistence.PointJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserJpaRepository;
import hhplus.hhplusconcertreservation.interfaces.presetation.concert.dto.response.ConcertPaymentResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
class ConcertControllerIntegrationTest {
	@Value("${variables.authTokenSecretKey}")
	private String authTokenSecretKey;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private PointJpaRepository pointJpaRepository;

	@Autowired
	private ConcertJpaRepository concertJpaRepository;

	@Autowired
	private ConcertScheduleJpaRepository concertScheduleJpaRepository;

	@Autowired
	private ConcertSeatJpaRepository concertSeatJpaRepository;

	@Autowired
	private ConcertBookingJpaRepository concertBookingJpaRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	public void cleanup() {
		databaseCleanUp.execute();
	}

	@Test
	public void 좌석_동시_결제() throws InterruptedException {
		// given
		UserEntity givenUser = userJpaRepository.save(UserEntity.builder()
			.name("테스트")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		PointEntity givenPoint = pointJpaRepository.save(PointEntity.builder()
			.user(givenUser)
			.amount(1000L)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		ConcertEntity concert = concertJpaRepository.save(ConcertEntity.builder()
			.title("테스트 타이틀")
			.description("테스트 설명")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		ConcertScheduleEntity concertSchedule = concertScheduleJpaRepository.save(ConcertScheduleEntity.builder()
			.concert(concert)
			.bookingStartDate(LocalDateTime.now().minusDays(1))
			.bookingEndDate(LocalDateTime.now().plusDays(1))
			.performanceDate(LocalDateTime.now().plusDays(2))
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		ConcertSeatEntity givenConcertSeat = concertSeatJpaRepository.save(ConcertSeatEntity.builder()
			.concert(concert)
			.concertSchedule(concertSchedule)
			.number(1)
			.price(1000)
			.isPaid(false)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		ConcertBookingEntity givenConcertBooking = concertBookingJpaRepository.save(ConcertBookingEntity.builder()
			.user(givenUser)
			.concert(concert)
			.concertSchedule(concertSchedule)
			.concertSeat(givenConcertSeat)
			.price(givenConcertSeat.getPrice())
			.isPaid(false)
			.expiresDate(LocalDateTime.now().plusDays(1))
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		int threadCount = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		List<Callable<Boolean>> callables = new ArrayList<>();

		SecretKey key = Keys.hmacShaKeyFor(authTokenSecretKey.getBytes());
		String authToken = "Bearer " + Jwts.builder()
			.claim("userId", givenUser.getId())
			.signWith(key)
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
			.compact();

		for (int i = 0; i < threadCount; i++) {
			callables.add(() -> {
				try {
					MvcResult result = mockMvc.perform(post("/concerts/schedules/seats/bookings/" + givenConcertBooking.getId() + "/pay")
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", authToken))
						.andExpect(status().isOk())
						.andReturn();
					return result.getResponse().getStatus() == 200;
				} catch (Exception e) {
					return false;
				}
			});
		}

		// when
		List<Future<Boolean>> futures = executorService.invokeAll(callables);
		executorService.shutdown();

		// then
		long successCount = futures.stream().filter(f -> {
			try {
				return f.get();
			} catch (Exception e) {
				return false;
			}
		}).count();
		long failureCount = threadCount - successCount;

		assertEquals(1, successCount);
		assertEquals(9, failureCount);

		pointJpaRepository.findByUserId(givenUser.getId()).ifPresent(pointEntity ->
			assertEquals(givenPoint.getAmount() - givenConcertBooking.getPrice(), pointEntity.getAmount())
		);

		concertSeatJpaRepository.findById(givenConcertSeat.getId()).ifPresent(concertSeat ->
			assertEquals(true, concertSeat.getIsPaid())
		);

		concertBookingJpaRepository.findById(givenConcertBooking.getId()).ifPresent(concertBooking ->
			assertEquals(true, concertBooking.getIsPaid())
		);
	}
}