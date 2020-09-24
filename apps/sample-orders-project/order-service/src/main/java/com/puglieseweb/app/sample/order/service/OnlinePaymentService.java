package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.OnlinePaymentRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.OnlinePaymentAction;

@Service
public class OnlinePaymentService {

	@Autowired
	private OnlinePaymentAction action;

	public void pay(OnlinePaymentRequest request) {
		action.publishPaymentToKafka(request);
	}

}
