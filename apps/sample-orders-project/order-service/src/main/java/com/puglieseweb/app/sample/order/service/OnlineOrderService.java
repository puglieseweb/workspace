package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.OnlineOrderRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.OnlineOrderAction;

@Service
public class OnlineOrderService {

	@Autowired
	private OnlineOrderAction action;

	public void saveOnlineOrder(OnlineOrderRequest request) {
		action.publishToKafka(request);
	}

}
