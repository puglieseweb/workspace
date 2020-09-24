package com.course.kafka.api.request;

import java.time.LocalDateTime;

import com.course.kafka.util.LocalDateTimeDeserializer;
import com.course.kafka.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class WebColorVoteRequest {

	private String color;

	private String username;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime voteDateTime;

	public String getColor() {
		return color;
	}

	public String getUsername() {
		return username;
	}

	public LocalDateTime getVoteDateTime() {
		return voteDateTime;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVoteDateTime(LocalDateTime voteDateTime) {
		this.voteDateTime = voteDateTime;
	}

	@Override
	public String toString() {
		return "WebColorVoteRequest [color=" + color + ", voteDateTime=" + voteDateTime + ", username=" + username
				+ "]";
	}

}
