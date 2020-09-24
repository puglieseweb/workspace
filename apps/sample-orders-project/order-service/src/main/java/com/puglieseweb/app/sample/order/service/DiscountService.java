package com.puglieseweb.app.sample.order.service;

import com.puglieseweb.app.sample.order.domain.request.DiscountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.adapter.broker.action.DiscountAction;

@Service
public class DiscountService {

	@Autowired
	private DiscountAction action;

	public void createDiscount(DiscountRequest request) {
		action.publishToKafka(request);
	}

}
