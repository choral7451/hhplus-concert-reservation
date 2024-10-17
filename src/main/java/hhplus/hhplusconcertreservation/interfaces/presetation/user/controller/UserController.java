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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserQueueService userQueueService;

	@PostMapping("/queue")
	public String issueToken(@RequestBody CreateUserQueueRequest request) {
		return userQueueService.issueToken(request.getUserId());
	}

	@GetMapping("/queue/order")
	public UserQueueResponse userQueue(@RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "");

		return new UserQueueResponse(userQueueService.scanUserQueue(jwtToken));
	}
}

