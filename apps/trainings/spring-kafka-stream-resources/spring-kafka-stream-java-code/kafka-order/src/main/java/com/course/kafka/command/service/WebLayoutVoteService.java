package com.course.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.kafka.api.request.WebLayoutVoteRequest;
import com.course.kafka.command.action.WebLayoutVoteAction;

@Service
public class WebLayoutVoteService {

	@Autowired
	private WebLayoutVoteAction action;

	public void createLayoutVote(WebLayoutVoteRequest request) {
		action.publishToKafka(request);
	}

}
