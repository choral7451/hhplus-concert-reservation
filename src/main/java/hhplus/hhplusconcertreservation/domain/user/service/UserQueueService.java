package hhplus.hhplusconcertreservation.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.enums.UserQueueStatus;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserNotFound;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserQueueNotFound;
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
			.orElseThrow(UserNotFound::new);

		UserQueue userQueue = userQueueRepository.findByUserId(userId)
			.orElseGet(() -> userQueueRepository.save(userId, tokenService.issueWaitingToken(userId)));

		return userQueue.getToken();
	}

	public UserQueue scanUserQueue(String jwtToken) {
		Long userId = tokenService.getUserIdByWaitingToken(jwtToken);

		UserQueue userQueue = userQueueRepository.findByUserId(userId).orElseThrow(UserQueueNotFound::new);

		if (userQueue.getStatus() == UserQueueStatus.WAITING) {
			int currentOrder = userQueueRepository.countCurrentOrderByUserId(userId);
			userQueue.setCurrentOrder(currentOrder);
		}

		return userQueue;
	}

	public void deleteExpiredUserQueues() {
		userQueueRepository.deleteExpiredUserQueues();
	}

	public void activeUserQueues() {
		int activeUser = userQueueRepository.countActiveUserQueues();
		LocalDateTime expiresDate = LocalDateTime.now().plusMinutes(WAITING_TOKEN_ACTIVE_TIME_MINUTES);
		userQueueRepository.activateUserQueues(MAXIMUM_ACTIVE_USER - activeUser, expiresDate);
	}
}
