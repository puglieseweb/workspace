package com.puglieseweb.app.sample.order.adapter.broker.action;

import com.puglieseweb.app.sample.order.domain.request.DiscountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.adapter.broker.message.DiscountMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.DiscountProducer;

@Component
public class DiscountAction {

	@Autowired
	private DiscountProducer producer;

	public void publishToKafka(DiscountRequest request) {
		var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
		producer.publish(message);
	}

}
