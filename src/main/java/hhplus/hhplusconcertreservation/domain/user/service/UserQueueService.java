package hhplus.hhplusconcertreservation.domain.user.service;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserQueueRepository;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserNotFound;
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
}
