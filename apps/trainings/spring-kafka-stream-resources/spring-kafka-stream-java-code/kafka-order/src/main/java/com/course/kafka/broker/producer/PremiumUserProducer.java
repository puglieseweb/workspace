package com.course.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.PremiumUserMessage;

@Service
public class PremiumUserProducer {

	@Autowired
	private KafkaTemplate<String, PremiumUserMessage> kafkaTemplate;

	public void publish(PremiumUserMessage message) {
		kafkaTemplate.send("t.commodity.premium-user", message.getUsername(), message);
	}

}
