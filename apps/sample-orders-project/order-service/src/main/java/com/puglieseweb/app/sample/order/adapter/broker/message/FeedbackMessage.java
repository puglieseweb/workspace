package com.puglieseweb.app.sample.order.adapter.broker.message;

import java.time.LocalDateTime;

import com.puglieseweb.app.sample.order.util.LocalDateTimeDeserializer;
import com.puglieseweb.app.sample.order.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class FeedbackMessage {

	private String feedback;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime feedbackDateTime;

	private String location;

	private int rating;

	public String getFeedback() {
		return feedback;
	}

	public LocalDateTime getFeedbackDateTime() {
		return feedbackDateTime;
	}

	public String getLocation() {
		return location;
	}

	public int getRating() {
		return rating;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setFeedbackDateTime(LocalDateTime feedbackDateTime) {
		this.feedbackDateTime = feedbackDateTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "FeedbackMessage [feedback=" + feedback + ", feedbackDateTime=" + feedbackDateTime + ", location="
				+ location + ", rating=" + rating + "]";
	}

}
