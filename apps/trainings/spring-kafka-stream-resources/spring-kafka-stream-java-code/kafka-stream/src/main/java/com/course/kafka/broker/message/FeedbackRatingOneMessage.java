package com.course.kafka.broker.message;

public class FeedbackRatingOneMessage {

	private String location;
	private double averageRating;

	public double getAverageRating() {
		return averageRating;
	}

	public String getLocation() {
		return location;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "FeedbackRatingOneMessage [location=" + location + ", averageRating=" + averageRating + "]";
	}

}
