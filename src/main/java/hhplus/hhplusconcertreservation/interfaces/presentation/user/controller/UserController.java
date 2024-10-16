package hhplus.hhplusconcertreservation.interfaces.presentation.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.domain.user.model.UserQueue;
import hhplus.hhplusconcertreservation.domain.user.service.UserQueueService;
import hhplus.hhplusconcertreservation.interfaces.presentation.user.dto.request.CreaterUserQueueRequest;
import hhplus.hhplusconcertreservation.interfaces.presentation.user.dto.response.UserQueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "유저 API", description = "유저와 관련된 API 입니다. 모든 API는 대기열 토큰 헤더(Authorization) 가 필요합니다.")
public class UserController {

	private final UserQueueService userQueueService;

	@Operation(summary = "유저 대기열 입장")
	@PostMapping("/queue")
	public UserQueueResponse createUserQueue(@RequestBody CreaterUserQueueRequest request) {
		UserQueue userQueue = userQueueService.create(request.getUserId());
		return UserQueueResponse.builder().userQueue(userQueue).build();
	}
}
