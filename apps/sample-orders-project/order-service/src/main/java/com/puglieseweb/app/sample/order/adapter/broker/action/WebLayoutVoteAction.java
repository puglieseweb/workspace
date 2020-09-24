package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.WebLayoutVoteRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.WebLayoutVoteMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.WebLayoutVoteProducer;

@Component
public class WebLayoutVoteAction {

	@Autowired
	private WebLayoutVoteProducer producer;

	public void publishToKafka(WebLayoutVoteRequest request) {
		var message = new WebLayoutVoteMessage();

		message.setUsername(request.getUsername());
		message.setLayout(request.getLayout());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
