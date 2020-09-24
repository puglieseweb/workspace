package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.PremiumUserRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.PremiumUserMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.PremiumUserProducer;

@Component
public class PremiumUserAction {

	@Autowired
	private PremiumUserProducer producer;

	public void publishToKafka(PremiumUserRequest request) {
		var message = new PremiumUserMessage();

		message.setUsername(request.getUsername());
		message.setLevel(request.getLevel());

		producer.publish(message);
	}

}
