package hhplus.hhplusconcertreservation.interfaces.presetation.point.dto.response;

import hhplus.hhplusconcertreservation.domain.point.model.Point;
import lombok.Getter;

@Getter
public class PointResponse {
	private final Long id;
	private final Long amount;

	public PointResponse(Point point) {
		this.id = point.getId();
		this.amount = point.getAmount();
	}
}
