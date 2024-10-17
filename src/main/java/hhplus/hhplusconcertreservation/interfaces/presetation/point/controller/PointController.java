package hhplus.hhplusconcertreservation.interfaces.presetation.point.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.point.service.PointService;
import hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.response.PointResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {
	private final PointService pointService;

	@GetMapping("/{userId}")
	public PointResponse scanPoint(
		@PathVariable Long userId,
		@RequestHeader("Authorization") String token
	) {
		String jwtToken = token.replace("Bearer ", "");

		return new PointResponse(pointService.scanPoint(jwtToken));
	}
}
