package hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChargePointRequest {
	@Schema(type = "Number", description = "충전 금액", defaultValue = "5000")
	private Long amount;
}
