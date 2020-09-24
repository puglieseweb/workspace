package com.course.kafka.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.kafka.api.request.PremiumUserRequest;
import com.course.kafka.command.service.PremiumUserService;

@RestController
@RequestMapping("/api/premium/user")
public class PremiumUserApi {

	@Autowired
	private PremiumUserService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createUser(@RequestBody PremiumUserRequest request) {
		service.createUser(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Premium user created, username : " + request.getUsername() + ", level : " + request.getLevel());
	}

}
