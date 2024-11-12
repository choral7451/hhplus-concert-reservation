package hhplus.hhplusconcertreservation.domain.user.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
import hhplus.hhplusconcertreservation.domain.token.model.Token;
import hhplus.hhplusconcertreservation.domain.token.repository.TokenRepository;
import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueueService {

	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final ObjectMapper objectMapper;

	public String issueToken(Long userId) {
		userRepository.findByUserId(userId)
			.orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));

		String tokenKey = UUID.randomUUID().toString();
		Token token = new Token(tokenKey, userId.toString());

		try {
			String tokenJson = objectMapper.writeValueAsString(token);
			tokenRepository.zaddWaitingToken(tokenJson);
			} catch (IOException e) {
			throw new CoreException(ErrorType.FAilED_TO_ISSUE_WAITING_TOKEN, Map.of("userId", userId, "token", tokenKey));
		}

		return tokenKey;
	}

	public UserQueue scanUserQueue(Long userId, String tokenKey) {
		userRepository.findByUserId(userId)
			.orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));

		boolean hasToken = tokenRepository.hasActiveToken(userId.toString());
		if (hasToken) {
			return UserQueue.setActive();
		}

		try {
			Token token = new Token(tokenKey, userId.toString());
			String tokenJson = objectMapper.writeValueAsString(token);
			Long myRank = tokenRepository.countCurrentOrderByToken(tokenJson);
			if(myRank == null){
				throw new CoreException(ErrorType.USER_QUEUE_NOT_FOUND, Map.of("userId", userId, "token", token));
			} else {
				return UserQueue.setWaiting(myRank);
			}

		} catch (IOException e) {
			throw new CoreException(ErrorType.FAilED_TO_ISSUE_WAITING_TOKEN, Map.of("userId", userId, "token", tokenKey));
		}
	}

	public void activeUserQueues() {
		boolean hasTokens = tokenRepository.hasActiveTokens();
		if (!hasTokens) {
			Set<String> tokens = tokenRepository.getWaitingTopTokens(10);

			if(!tokens.isEmpty()) {
				tokenRepository.removeWaitingTokens(tokens);

				Set<String> userIds = tokens.stream()
					.map(tokenJson -> {
						try {
							Token token = objectMapper.readValue(tokenJson, Token.class);
							return token.getUserId();
						} catch (IOException e) {
							e.printStackTrace();
							return null;
						}
					})
					.collect(Collectors.toSet());
				tokenRepository.setActiveTokens(userIds);
			}

		}
	}
}
