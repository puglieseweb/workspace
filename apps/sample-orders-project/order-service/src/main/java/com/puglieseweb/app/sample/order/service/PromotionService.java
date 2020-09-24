package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.PromotionRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.PromotionAction;

@Service
public class PromotionService {

	@Autowired
	private PromotionAction action;

	public void createPromotion(PromotionRequest request) {
		action.publishToKafka(request);
	}

}
