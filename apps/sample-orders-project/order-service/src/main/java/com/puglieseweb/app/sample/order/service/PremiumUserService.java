package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.PremiumUserRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.PremiumUserAction;

@Service
public class PremiumUserService {

	@Autowired
	private PremiumUserAction action;

	public void createUser(PremiumUserRequest request) {
		action.publishToKafka(request);
	}

}
