package com.puglieseweb.app.sample.order.domain.request;

import java.time.LocalDateTime;

import com.puglieseweb.app.sample.order.util.LocalDateTimeDeserializer;
import com.puglieseweb.app.sample.order.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class InventoryRequest {

	private String item;
	private long quantity;
	private String location;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime transactionTime;

	public String getItem() {
		return item;
	}

	public String getLocation() {
		return location;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "InventoryRequest [item=" + item + ", quantity=" + quantity + ", location=" + location
				+ ", transactionTime=" + transactionTime + "]";
	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}

}
