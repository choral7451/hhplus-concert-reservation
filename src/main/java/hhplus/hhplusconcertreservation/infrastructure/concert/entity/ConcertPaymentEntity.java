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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "concert_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertPaymentEntity {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_booking_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConcertBookingEntity concertBooking;

	@Column(name = "price", nullable = false)
	private Integer price;

	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Builder
	public ConcertPaymentEntity(Long id, UserEntity user, ConcertEntity concert, ConcertScheduleEntity concertSchedule,
		ConcertSeatEntity concertSeat, ConcertBookingEntity concertBooking, Integer price, LocalDateTime createdDate,
		LocalDateTime updatedDate) {
		this.id = id;
		this.user = user;
		this.concert = concert;
		this.concertSchedule = concertSchedule;
		this.concertSeat = concertSeat;
		this.concertBooking = concertBooking;
		this.price = price;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
