package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.PremiumPurchaseRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.PremiumPurchaseMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.PremiumPurchaseProducer;

@Component
public class PremiumPurchaseAction {

	@Autowired
	private PremiumPurchaseProducer producer;

	public void publishToKafka(PremiumPurchaseRequest request) {
		var message = new PremiumPurchaseMessage();

		message.setUsername(request.getUsername());
		message.setItem(request.getItem());
		message.setPurchaseNumber(request.getPurchaseNumber());

		producer.publish(message);
	}

}
