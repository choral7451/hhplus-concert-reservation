package hhplus.hhplusconcertreservation.interfaces.presetation.point.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.point.service.PointService;
import hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.request.ChargePointRequest;
import hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.response.PointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "POINT")
@RequestMapping("/points")
public class PointController {
	private final PointService pointService;

	@Operation(summary = "포인트 조회")
	@GetMapping("/{userId}")
	public PointResponse scanPoint(
		@RequestHeader("Authorization") String token
	) {
		String jwtToken = token.replace("Bearer ", "");

		return new PointResponse(pointService.scanPoint(jwtToken));
	}

	@Operation(summary = "포인트 충전")
	@PatchMapping("/{userId}/charge")
	public PointResponse chargePoint(
		@RequestHeader("Authorization") String token,
		@RequestBody() ChargePointRequest request
	) {
		String jwtToken = token.replace("Bearer ", "");

		return new PointResponse(pointService.chargePoint(jwtToken, request.getAmount()));
	}
}
