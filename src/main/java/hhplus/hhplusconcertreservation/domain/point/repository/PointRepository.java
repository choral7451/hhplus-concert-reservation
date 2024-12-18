package hhplus.hhplusconcertreservation.domain.point.repository;

import java.util.Optional;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.user.model.User;

public interface PointRepository {
	Optional<Point> findByUserId(Long userId);
	Optional<Point> findByUserIdWithLock(Long userId);
	Point update(Point point);
	Point save(User user);
}
