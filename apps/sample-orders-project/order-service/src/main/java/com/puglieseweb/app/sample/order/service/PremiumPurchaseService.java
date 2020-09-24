package com.puglieseweb.app.sample.order.service;

import com.puglieseweb.app.sample.order.adapter.broker.action.PremiumPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.PremiumPurchaseRequest;

@Service
public class PremiumPurchaseService {

	@Autowired
	private PremiumPurchaseAction action;

	public void createPurchase(PremiumPurchaseRequest request) {
		action.publishToKafka(request);
	}

}
