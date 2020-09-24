package com.puglieseweb.app.sample.order.adapter.broker.action;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import com.puglieseweb.app.sample.order.domain.request.FeedbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.adapter.broker.message.FeedbackMessage;
import com.puglieseweb.app.sample.order.adapter.broker.producer.FeedbackProducer;

@Component
public class FeedbackAction {

	@Autowired
	private FeedbackProducer producer;

	public void publishToKafka(FeedbackRequest request) {
		var message = new FeedbackMessage();
		message.setFeedback(request.getFeedback());
		message.setLocation(request.getLocation());
		message.setRating(request.getRating());
		// random date time between last 7 days - now
		message.setFeedbackDateTime(LocalDateTime.now().minusHours(ThreadLocalRandom.current().nextLong(7 * 7)));

		producer.publish(message);
	}

}
