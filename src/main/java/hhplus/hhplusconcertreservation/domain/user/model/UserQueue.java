package hhplus.hhplusconcertreservation.domain.user.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Jwts;

public class UserQueue {
	private Long id;
	private Long userId;
	private String token;
	private LocalDateTime expiresDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public UserQueue(Long id, Long userId, String token, LocalDateTime expiresDate, LocalDateTime createdDate,
		LocalDateTime updatedDate) {
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public UserQueue(Long userId) {
		this.userId = userId;
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	void activate() {
		this.token = Jwts.builder()
			.claim("userId", userId)
			.claim("token", UUID.randomUUID().toString())
			.claim("enteredDt", new Date())
			.claim("expiredDt", new Date(System.currentTimeMillis() + 300000)) // exp: 5분 후 만료
			.compact();
		this.expiresDate = LocalDateTime.now().plusMinutes(5);
	}


	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getExpiresDate() {
		return expiresDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
}
