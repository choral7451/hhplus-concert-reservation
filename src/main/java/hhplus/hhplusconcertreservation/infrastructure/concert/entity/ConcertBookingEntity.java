package hhplus.hhplusconcertreservation.infrastructure.concert.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import hhplus.hhplusconcertreservation.infrastructure.user.entity.UserEntity;
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
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "concert_booking")
public class ConcertBookingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConcertEntity concert;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_schedule_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConcertScheduleEntity concertSchedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConcertSeatEntity concertSeat;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "is_paid", nullable = false)
	private Boolean isPaid;

	@Column(name = "expires_date", nullable = false)
	private LocalDateTime expiresDate;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Builder
	public ConcertBookingEntity(Long id, UserEntity user, ConcertEntity concert, ConcertScheduleEntity concertSchedule,
		ConcertSeatEntity concertSeat, Integer price, Boolean isPaid, LocalDateTime expiresDate, LocalDateTime createdDate,
		LocalDateTime updatedDate) {
		this.id = id;
		this.user = user;
		this.concert = concert;
		this.concertSchedule = concertSchedule;
		this.concertSeat = concertSeat;
		this.price = price;
		this.isPaid = isPaid;
		this.expiresDate = expiresDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
