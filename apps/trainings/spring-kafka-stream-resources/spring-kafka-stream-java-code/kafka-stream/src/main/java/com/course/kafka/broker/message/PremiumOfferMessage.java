package com.course.kafka.broker.message;

public class PremiumOfferMessage {

	private String username;

	private String level;

	private String purchaseNumber;

	public String getLevel() {
		return level;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
