package com.puglieseweb.app.sample.order.service;

import com.puglieseweb.app.sample.order.domain.request.FeedbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.adapter.broker.action.FeedbackAction;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackAction action;

	public void createFeedback(FeedbackRequest request) {
		action.publishToKafka(request);
	}

}
