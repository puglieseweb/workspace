package com.puglieseweb.app.sample.order.adapter.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.adapter.broker.message.OnlineOrderMessage;

@Service
public class OnlineOrderProducer {

	@Autowired
	private KafkaTemplate<String, OnlineOrderMessage> kafkaTemplate;

	public void publish(OnlineOrderMessage message) {
		kafkaTemplate.send("t.commodity.online-order", message.getOnlineOrderNumber(), message);
	}

}