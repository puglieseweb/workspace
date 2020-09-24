package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.WebColorVoteRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.WebColorVoteAction;

@Service
public class WebColorVoteService {

	@Autowired
	private WebColorVoteAction action;

	public void createColorVote(WebColorVoteRequest request) {
		action.publishToKafka(request);
	}

}
