package com.hulkhiretech.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hulkhiretech.payments.constants.ErrorCodeEnum;
import com.hulkhiretech.payments.exception.PaypalProviderException;
import com.hulkhiretech.payments.pojo.CreateOrderReq;
import com.hulkhiretech.payments.pojo.Order;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/paypal/order")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	/**
	 * Create a PayPal order
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Order createOrder(@RequestBody CreateOrderReq createOrderReq) {
		log.info("createOrderReq: {}", createOrderReq);
		Order response = paymentService.createOrder(createOrderReq);
		log.info("response: {}", response);
		return response;
	}

	/**
	 * Get PayPal order details
	 */
	@GetMapping("/{orderId}")
	public Order getOrder(@PathVariable String orderId) {
		log.info("getOrder orderId: {}", orderId);

		// Example condition for simulating an error
		if (orderId.contains("TEMP2")) {
			throw new PaypalProviderException(
					ErrorCodeEnum.GENERIC_ERROR.getCode(),
					"TEMP2 error test case - simulated failure",
					HttpStatus.BAD_REQUEST
			);
		}

		Order response = paymentService.getOrder(orderId);
		log.info("response: {}", response);
		return response;
	}

	/**
	 * Capture a PayPal order
	 */
	@PostMapping("/{orderId}/capture")
	public Order captureOrder(@PathVariable String orderId) {
		log.info("captureOrder orderId: {}", orderId);
		Order response = paymentService.captureOrder(orderId);
		log.info("response: {}", response);
		return response;
	}
}
