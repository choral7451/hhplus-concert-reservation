package hhplus.hhplusconcertreservation.interfaces.api.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hhplus.hhplusconcertreservation.interfaces.api.common.dto.CreateResponse;
import hhplus.hhplusconcertreservation.interfaces.api.payment.dto.request.PayRequest;
import hhplus.hhplusconcertreservation.interfaces.api.payment.dto.request.PointChargeRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

	@PutMapping("/point/charge")
	public ResponseEntity<Void> charge(
		@RequestBody PointChargeRequest request
	) {

		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<CreateResponse> pay(
		@RequestBody PayRequest request
	) {
		return new ResponseEntity<>(CreateResponse.builder().id(1L).build(), HttpStatus.OK);
	}
}
