package com.puglieseweb.app.sample.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.domain.request.FlashSaleVoteRequest;
import com.puglieseweb.app.sample.order.adapter.broker.action.FlashSaleVoteAction;

@Service
public class FlashSaleVoteService {

	@Autowired
	private FlashSaleVoteAction action;

	public void createFlashSaleVote(FlashSaleVoteRequest request) {
		action.publishToKafka(request);
	}

}
