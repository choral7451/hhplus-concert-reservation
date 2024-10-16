package hhplus.hhplusconcertreservation.infrastructure.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_queues")
public class UserQueueEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "token")
	private String token;

	@Column(name = "expires_date")
	private LocalDateTime expiresDate;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Builder
	public UserQueueEntity(Long id, Long userId, String token, LocalDateTime expiresDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
