package hhplus.hhplusconcertreservation.domain.token.model;

public class Token {
	private final String token;
	private final String userId;

	public Token(String token, String userId) {
		this.token = token;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public String getUserId() {
		return userId;
	}
}
