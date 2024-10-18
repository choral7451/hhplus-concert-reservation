package hhplus.hhplusconcertreservation.interfaces.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hhplus.hhplusconcertreservation.domain.user.service.UserQueueService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WaitingScheduler {

	private final UserQueueService userQueueService;

	@Scheduled(cron = "*/10 * * * * *")
	public void deleteExpiredUserQueuesScheduler(){
		userQueueService.deleteExpiredUserQueues();
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void activeUserQueuesScheduler(){
		userQueueService.activeUserQueues();
	}
}
