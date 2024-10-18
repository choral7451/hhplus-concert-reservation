package hhplus.hhplusconcertreservation.infrastructure.concert.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "concerts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Builder
	public ConcertEntity(Long id, String title, String description, LocalDateTime createdDate,
		LocalDateTime updatedDate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
