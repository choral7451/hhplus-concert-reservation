package hhplus.hhplusconcertreservation.domain.user.service;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.repository.UserQueueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueueService {
	private final UserQueueRepository userQueueRepository;

	public UserQueue create(Long userId) {
		return userQueueRepository.save(new UserQueue(userId));
	}
}
