package hhplus.hhplusconcertreservation.domain.user.service;

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

	public String issueToken(Long userId) {
		userRepository.findByUserId(userId)
			.orElseThrow(UserNotFound::new);

		UserQueue userQueue = userQueueRepository.findByUserId(userId)
			.orElseGet(() -> userQueueRepository.save(userId, tokenService.issueToken(userId)));

		return userQueue.getToken();
	}

	public UserQueue scanUserQueue(String jwtToken) {
		Long userId = tokenService.getUserId(jwtToken);

		UserQueue userQueue = userQueueRepository.findByUserId(userId).orElseThrow(UserQueueNotFound::new);

		if(userQueue.getStatus() == UserQueueStatus.WAITING) {
			int currentOrder = userQueueRepository.countCurrentOrderByUserId(userId);
			userQueue.setCurrentOrder(currentOrder);
		}

		return userQueue;
	}
}
