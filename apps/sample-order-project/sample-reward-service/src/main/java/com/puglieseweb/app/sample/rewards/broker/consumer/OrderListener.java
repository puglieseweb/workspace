package com.puglieseweb.app.sample.rewards.broker.consumer;

import com.puglieseweb.app.sample.rewards.broker.message.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

//@Service
public class OrderListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderListener.class);

	@KafkaListener(topics = "t.commodity.order")
	public void listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
		var headers = consumerRecord.headers();
		var orderMessage = consumerRecord.value();

		LOG.info("Processing order {}, item {}, credit card number {}", orderMessage.getOrderNumber(),
				orderMessage.getItemName(), orderMessage.getCreditCardNumber());
		LOG.info("Headers are :");
		headers.forEach(h -> LOG.info("  key : {}, value : {}", h.key(), new String(h.value())));

		var bonusPercentage = Double.parseDouble(new String(headers.lastHeader("surpriseBonus").value()));
		var bonusAmount = (bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity();

		LOG.info("Surprise bonus is {}", bonusAmount);
	}

}
