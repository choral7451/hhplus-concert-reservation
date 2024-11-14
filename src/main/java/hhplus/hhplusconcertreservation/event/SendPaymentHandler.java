package hhplus.hhplusconcertreservation.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendPaymentHandler {

	@Async
	@EventListener
	public void sendPayment(SendPaymentEvent event) {
		log.info("------------ 결제 정보 전송 성고 ------------");
		log.info("------------ 제목 : {} ------------", event.getTitle());
		log.info("------------ 일자 : {} ------------", event.getPerformanceDate());
		log.info("------------ 가격 : {} ------------", event.getPrice());
	}
}
