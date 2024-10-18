package hhplus.hhplusconcertreservation.infrastructure.concert.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "concert_schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConcertEntity concert;

	@Column(name = "booking_start_date", nullable = false)
	private LocalDateTime bookingStartDate;

	@Column(name = "booking_end_date", nullable = false)
	private LocalDateTime bookingEndDate;

	@Column(name = "performance_date", nullable = false)
	private LocalDateTime performanceDate;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Builder
	public ConcertScheduleEntity(Long id, ConcertEntity concert, LocalDateTime bookingStartDate,
		LocalDateTime bookingEndDate, LocalDateTime performanceDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.concert = concert;
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
		this.performanceDate = performanceDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
