package hhplus.hhplusconcertreservation.interfaces.presetation.point.controller;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.point.service.PointService;
import hhplus.hhplusconcertreservation.infrastructure.common.redis.DistributedLock;
import hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.request.ChargePointRequest;
import hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.response.PointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "POINT")
@RequestMapping("/points")
public class PointController {
	private final PointService pointService;

	@Operation(summary = "포인트 조회")
	@GetMapping("/{userId}")
	public PointResponse scanPoint(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		return new PointResponse(pointService.scanPoint(userId));
	}

	@Operation(summary = "포인트 충전")
	@PatchMapping("/{userId}/charge")
	public PointResponse chargePoint(HttpServletRequest request, @RequestBody() ChargePointRequest body) {
		Long userId = (Long) request.getAttribute("userId");
		return new PointResponse(pointService.chargePoint(userId, body.getAmount()));
	}
}
