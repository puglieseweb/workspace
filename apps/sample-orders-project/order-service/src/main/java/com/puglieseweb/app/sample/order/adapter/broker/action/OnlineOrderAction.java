package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.OnlineOrderRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.OnlineOrderMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.OnlineOrderProducer;

@Component
public class OnlineOrderAction {

	@Autowired
	private OnlineOrderProducer producer;

	public void publishToKafka(OnlineOrderRequest request) {
		var message = new OnlineOrderMessage();

		message.setOnlineOrderNumber(request.getOnlineOrderNumber());
		message.setOrderDateTime(request.getOrderDateTime());
		message.setTotalAmount(request.getTotalAmount());
		message.setUsername(request.getUsername().toLowerCase());

		producer.publish(message);
	}

}
