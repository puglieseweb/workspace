package com.puglieseweb.app.sample.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puglieseweb.app.sample.order.domain.request.DiscountRequest;
import com.puglieseweb.app.sample.order.service.DiscountService;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

	@Autowired
	private DiscountService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> create(@RequestBody DiscountRequest request) {
		service.createDiscount(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(request.getDiscountCode() + " with " + request.getDiscountPercentage() + "% discount");
	}

}
