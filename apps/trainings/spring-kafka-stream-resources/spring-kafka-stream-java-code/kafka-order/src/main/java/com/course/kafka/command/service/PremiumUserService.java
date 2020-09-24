package com.course.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.kafka.api.request.PremiumUserRequest;
import com.course.kafka.command.action.PremiumUserAction;

@Service
public class PremiumUserService {

	@Autowired
	private PremiumUserAction action;

	public void createUser(PremiumUserRequest request) {
		action.publishToKafka(request);
	}

}
