package com.puglieseweb.app.sample.order.adapter.broker.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.domain.request.WebColorVoteRequest;
import com.puglieseweb.app.sample.order.adapter.broker.message.WebColorVoteMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.WebColorVoteProducer;

@Component
public class WebColorVoteAction {

	@Autowired
	private WebColorVoteProducer producer;

	public void publishToKafka(WebColorVoteRequest request) {
		var message = new WebColorVoteMessage();

		message.setUsername(request.getUsername());
		message.setColor(request.getColor());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
