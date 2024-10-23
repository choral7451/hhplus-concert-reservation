package hhplus.hhplusconcertreservation.infrastructure.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import hhplus.hhplusconcertreservation.infrastructure.user.enums.UserQueueEntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_queues")
@SQLDelete(sql = "UPDATE user_queues SET deleted_date = NOW() WHERE id = ?")
@SQLRestriction("deleted_date is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQueueEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private UserQueueEntityStatus status;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "expires_date")
	private LocalDateTime expiresDate;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Column(name = "deleted_date")
	private LocalDateTime deletedDate;

	@Builder
	public UserQueueEntity(Long id, Long userId, UserQueueEntityStatus status, String token, LocalDateTime expiresDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.token = token;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
