package hhplus.hhplusconcertreservation.domain.token.repository;

import java.util.Set;

public interface TokenRepository {
	void zaddWaitingToken(String tokenJson);
	boolean hasActiveTokens();
	Set<String> getWaitingTopTokens(int count);
	Long countCurrentOrderByToken(String tokenJson);
	void removeWaitingTokens(Set<String> tokens);
	void setActiveTokens(Set<String> tokens);
	Boolean hasActiveToken(String userId);
}
