package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.PromotionRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.PromotionMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.PromotionProducer;

@Component
public class PromotionAction {

	@Autowired
	private PromotionProducer producer;

	public void publishToKafka(PromotionRequest request) {
		var message = new PromotionMessage(request.getPromotionCode());

		producer.publish(message);
	}

}
