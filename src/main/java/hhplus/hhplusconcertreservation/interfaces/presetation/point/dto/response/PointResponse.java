package hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.response;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PointResponse {
	@Schema(type = "Number", description = "포인트 아이디", defaultValue = "1")
	private final Long id;

	@Schema(type = "Number", description = "포인트 금액", defaultValue = "5000")
	private final Long amount;

	public PointResponse(Point point) {
		this.id = point.getId();
		this.amount = point.getAmount();
	}
}
