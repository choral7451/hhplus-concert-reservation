package hhplus.hhplusconcertreservation.interfaces.api.payment.dto.request;

import lombok.Getter;

@Getter
public class PointChargeRequest {
	private Long userId;
	private Long amount;
}
