package com.puglieseweb.app.sample.order.command.action;

import com.puglieseweb.app.sample.order.broker.message.DiscountMessage;
import com.puglieseweb.app.sample.order.broker.producer.DiscountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.api.request.DiscountRequest;

@Component
public class DiscountAction {

	@Autowired
	private DiscountProducer producer;

	public void publishToKafka(DiscountRequest request) {
		var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
		producer.publish(message);
	}

}
