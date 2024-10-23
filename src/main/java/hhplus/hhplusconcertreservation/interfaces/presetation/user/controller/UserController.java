package hhplus.hhplusconcertreservation.interfaces.presetation.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.user.service.UserQueueService;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.request.CreateUserQueueRequest;
import hhplus.hhplusconcertreservation.interfaces.presetation.user.dto.response.UserQueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "USER")
public class UserController {

	private final UserQueueService userQueueService;

	@Operation(summary = "토큰 발행 API")
	@PostMapping("/queue")
	public String issueToken(@RequestBody CreateUserQueueRequest request) {
		return userQueueService.issueToken(request.getUserId());
	}

	@Operation(summary = "대기 상태 조회")
	@GetMapping("/queue/order")
	public UserQueueResponse userQueue(@RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "");

		return new UserQueueResponse(userQueueService.scanUserQueue(jwtToken));
	}
}

