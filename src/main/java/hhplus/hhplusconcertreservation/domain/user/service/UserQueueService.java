package hhplus.hhplusconcertreservation.domain.user.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueueService {

	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final UserQueueRepository userQueueRepository;

	private static final int MAXIMUM_ACTIVE_USER = 10;
	private static final int WAITING_TOKEN_ACTIVE_TIME_MINUTES = 5;

	public String issueToken(Long userId) {
		userRepository.findByUserId(userId)
			.orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));

		UserQueue userQueue = userQueueRepository.findByUserId(userId)
			.orElseGet(() -> userQueueRepository.save(userId, tokenService.issueWaitingToken(userId)));

		return userQueue.getToken();
	}

	public UserQueue scanUserQueue(String jwtToken) {
		Long userId = tokenService.getUserIdByWaitingToken(jwtToken);

		UserQueue userQueue = userQueueRepository.findByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.USER_QUEUE_NOT_FOUND, Map.of("userId", userId)));

		if (userQueue.isWaiting()) {
			int currentOrder = userQueueRepository.countCurrentOrderByUserId(userId);
			userQueue.setCurrentOrder(currentOrder);
		}

		return userQueue;
	}

	public void deleteExpiredUserQueues() {
		userQueueRepository.deleteExpiredUserQueues();
	}

	public void activeUserQueues() {
		int activeUsers = userQueueRepository.countActiveUserQueues();
		LocalDateTime expiresDate = LocalDateTime.now().plusMinutes(WAITING_TOKEN_ACTIVE_TIME_MINUTES);
		userQueueRepository.activateUserQueues(MAXIMUM_ACTIVE_USER - activeUsers, expiresDate);
	}
}
