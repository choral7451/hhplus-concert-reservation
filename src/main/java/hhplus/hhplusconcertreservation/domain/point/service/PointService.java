package hhplus.hhplusconcertreservation.domain.point.service;

import org.springframework.stereotype.Service;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import hhplus.hhplusconcertreservation.domain.point.repository.PointRepository;
import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.model.User;
import hhplus.hhplusconcertreservation.domain.user.respository.UserRepository;
import hhplus.hhplusconcertreservation.domain.user.service.exception.UserNotFound;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {
	private final PointRepository pointRepository;
	private final UserRepository userRepository;
	private final TokenService tokenService;

	public Point scanPoint(String token) {
		Long userId = tokenService.getUserIdByAuthToken(token);

		User user = userRepository.findByUserId(userId).orElseThrow(UserNotFound::new);
		return pointRepository.findByUserId(userId).orElseGet(() -> pointRepository.save(user));
	}
}
