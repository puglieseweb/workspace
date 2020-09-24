package com.puglieseweb.app.sample.storage.adapter.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.storage.adapter.broker.message.DiscountMessage;
import com.puglieseweb.app.sample.storage.adapter.broker.message.PromotionMessage;

@Service
@KafkaListener(topics = "t.commodity.promotion")
public class PromotionListener {

	private static final Logger LOG = LoggerFactory.getLogger(PromotionListener.class);

	@KafkaHandler
	public void listenDiscount(DiscountMessage message) {
		LOG.info("Processing discount : {}", message);
	}

	@KafkaHandler
	public void listenPromotion(PromotionMessage message) {
		LOG.info("Processing promotion : {}", message);
	}

}
