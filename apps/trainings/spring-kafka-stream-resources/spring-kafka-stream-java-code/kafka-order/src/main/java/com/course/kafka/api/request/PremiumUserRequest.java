package com.course.kafka.api.request;

public class PremiumUserRequest {

	private String level;

	private String username;

	public String getLevel() {
		return level;
	}

	public String getUsername() {
		return username;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
