package hhplus.hhplusconcertreservation.interfaces.presetation.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.token.service.TokenService;
import hhplus.hhplusconcertreservation.domain.user.service.UserQueueService;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.request.CreateUserQueueRequest;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response.UserQueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "USER")
public class UserController {

	private final UserQueueService userQueueService;
	private final TokenService tokenService;

	@Operation(summary = "인증 토큰 발행 API")
	@PostMapping("/auth")
	public String issueAuthToken(@RequestBody CreateUserQueueRequest request) {
		return tokenService.issueAuthToken(request.getUserId());
	}

	@Operation(summary = "대기열 토큰 발행 API")
	@PostMapping("/queue")
	public String issueWaitingToken(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		return userQueueService.issueToken(userId);
	}

	@Operation(summary = "대기 상태 조회")
	@GetMapping("/queue/order")
	public UserQueueResponse userQueue(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		return new UserQueueResponse(userQueueService.scanUserQueue(userId));
	}
}

