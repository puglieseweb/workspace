package com.puglieseweb.app.sample.order.adapter.broker.action;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.OnlinePaymentRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.OnlinePaymentMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.OnlinePaymentProducer;

@Component
public class OnlinePaymentAction {

	@Autowired
	private OnlinePaymentProducer producer;

	public void publishPaymentToKafka(OnlinePaymentRequest request) {
		var message = new OnlinePaymentMessage();

		message.setOnlineOrderNumber(request.getOnlineOrderNumber());
		message.setPaymentNumber("PAY-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase());
		message.setPaymentDateTime(
				request.getPaymentDateTime() == null ? LocalDateTime.now() : request.getPaymentDateTime());
		message.setPaymentMethod(request.getPaymentMethod());

		producer.publish(message);
	}

}
