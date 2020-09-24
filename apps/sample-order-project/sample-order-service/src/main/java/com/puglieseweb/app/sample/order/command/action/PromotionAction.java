package com.puglieseweb.app.sample.order.command.action;

import com.puglieseweb.app.sample.order.broker.message.PromotionMessage;
import com.puglieseweb.app.sample.order.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.api.request.PromotionRequest;

@Component
public class PromotionAction {

	@Autowired
	private PromotionProducer producer;

	public void publishToKafka(PromotionRequest request) {
		var message = new PromotionMessage(request.getPromotionCode());

		producer.publish(message);
	}

}
