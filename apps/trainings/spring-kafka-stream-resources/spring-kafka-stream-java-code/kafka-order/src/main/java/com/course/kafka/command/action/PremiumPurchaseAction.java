package com.course.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.kafka.api.request.PremiumPurchaseRequest;
import com.course.kafka.broker.message.PremiumPurchaseMessage;
import com.course.kafka.broker.producer.PremiumPurchaseProducer;

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
