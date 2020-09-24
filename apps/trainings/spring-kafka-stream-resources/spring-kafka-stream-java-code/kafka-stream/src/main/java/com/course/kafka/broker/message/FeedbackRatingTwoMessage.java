package com.course.kafka.broker.message;

import java.util.Map;
import java.util.TreeMap;

public class FeedbackRatingTwoMessage {

	private String location;
	private double averageRating;
	private Map<Integer, Long> ratingMap = new TreeMap<>();

	public double getAverageRating() {
		return averageRating;
	}

	public String getLocation() {
		return location;
	}

	public Map<Integer, Long> getRatingMap() {
		return ratingMap;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRatingMap(Map<Integer, Long> ratingMap) {
		this.ratingMap = ratingMap;
	}

	@Override
	public String toString() {
		return "FeedbackRatingTwoMessage [location=" + location + ", averageRating=" + averageRating + ", ratingMap="
				+ ratingMap + "]";
	}

}
