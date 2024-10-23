package hhplus.hhplusconcertreservation.domain.point.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.common.exception.CoreException;
import hhplus.hhplusconcertreservation.domain.common.exception.ErrorType;
import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.point.repository.PointRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {
	private final PointRepository pointRepository;
	private final UserRepository userRepository;
	private final TokenService tokenService;

	public Point scanPoint(String token) {
		Long userId = tokenService.getUserIdByAuthToken(token);

		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));
		return pointRepository.findByUserId(userId).orElseGet(() -> pointRepository.save(user));
	}

	@Transactional
	public Point chargePoint(String token, Long amount) {
		Long userId = tokenService.getUserIdByAuthToken(token);

		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, Map.of("userId", userId)));
		Point point = pointRepository.findByUserId(userId).
			orElseGet(() -> pointRepository.save(user));
		point.charge(amount);

		return pointRepository.update(point);
	}
}
