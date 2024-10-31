package hhplus.hhplusconcertreservation.interfaces.presetation.point.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import hhplus.hhplusconcertreservation.common.DatabaseCleanUp;
import hhplus.hhplusconcertreservation.infrastructure.point.entity.PointEntity;
import hhplus.hhplusconcertreservation.infrastructure.point.persistence.PointJpaRepository;
import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;
import hhplus.hhplusconcertreservation.infrastructure.user.persistence.UserJpaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerIntegrationTest {
	@Value("${variables.authTokenSecretKey}")
	private String authTokenSecretKey;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private PointJpaRepository pointJpaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	public void cleanup() {
		databaseCleanUp.execute();
	}

	@Test
	public void 포인트_동시_충전_테스트() throws Exception {
		// given
		Long givenUserId = 1L;
		Long givenAmount = 500L;

		UserEntity user = userJpaRepository.save(UserEntity.builder()
			.id(givenUserId)
			.name("테스트")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build()
		);

		// pointJpaRepository.save(PointEntity.builder()
		// 	.user(user)
		// 	.amount(0L)
		// 	.createdDate(LocalDateTime.now())
		// 	.updatedDate(LocalDateTime.now())
		// 	.build()
		// );

		int threadCount = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		List<Callable<Void>> callables = new ArrayList<>();

		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("amount", givenAmount);

		String requestBody = objectMapper.writeValueAsString(requestMap);

		SecretKey key = Keys.hmacShaKeyFor(authTokenSecretKey.getBytes());
		String authToken = "Bearer " + Jwts.builder()
			.claim("userId", givenUserId)
			.signWith(key)
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
			.compact();

		for (int i = 0; i < threadCount; i++) {
			callables.add(() -> {
				mockMvc.perform(patch("/points/" + givenUserId + "/charge")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody)
						.header("Authorization", authToken))
					.andExpect(status().isOk());
				return null;
			});
		}

		// when
		List<Future<Void>> futures = executorService.invokeAll(callables);
		executorService.shutdown();

		// then
		long expectedTotalAmount = threadCount * givenAmount;
		Optional<PointEntity> updatedPointOptional = pointJpaRepository.findByUserId(givenUserId);

		assertTrue(updatedPointOptional.isPresent());
		PointEntity updatedPoint = updatedPointOptional.get();

		assertEquals(expectedTotalAmount , updatedPoint.getAmount());
	}

}
