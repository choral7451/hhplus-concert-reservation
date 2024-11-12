package hhplus.hhplusconcertreservation.infrastructure.token.repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;


import hhplus.hhplusconcertreservation.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class TokenRedisRepositoryImpl implements TokenRepository {
	private final String WAITING_TOKEN_KEY = "waiting_tokens";
	private final String ACTIVE_TOKEN_KEY = "active_tokens";

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void zaddWaitingToken(String	tokenJson) {
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		double score = (double) LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		zSetOperations.add(WAITING_TOKEN_KEY, tokenJson, score);
	}

	@Override
	public boolean hasActiveTokens() {
		SetOperations<String, String> setOperations = redisTemplate.opsForSet();
		return setOperations.size(ACTIVE_TOKEN_KEY) > 0;
	}

	@Override
	public Set<String> getWaitingTopTokens(int count) {
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		return zSetOperations.reverseRange(WAITING_TOKEN_KEY, 0, count - 1);
	}

	@Override
	public void removeWaitingTokens(Set<String> tokens) {
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		zSetOperations.remove(WAITING_TOKEN_KEY, tokens.toArray());
	}

	@Override
	public Long countCurrentOrderByToken(String tokenJson) {
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		Long rank = zSetOperations.rank(WAITING_TOKEN_KEY, tokenJson);
		return rank != null ? rank + 1 : null;
	}

	@Override
	public void setActiveTokens(Set<String> userIds) {
		SetOperations<String, String> setOperations = redisTemplate.opsForSet();

		for (String token : userIds) {
			String key = ACTIVE_TOKEN_KEY + ':' + token;
			setOperations.add(key, token);
			redisTemplate.expire(key, Duration.ofMinutes(5));
		}
	}

	@Override
	public Boolean hasActiveToken(String userId) {
		SetOperations<String, String> setOperations = redisTemplate.opsForSet();
		return setOperations.isMember(ACTIVE_TOKEN_KEY + ':'+ userId, userId);
	}
}
