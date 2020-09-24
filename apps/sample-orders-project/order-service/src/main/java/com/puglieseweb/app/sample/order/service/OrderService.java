package com.puglieseweb.app.sample.order.service;

import com.puglieseweb.app.sample.order.domain.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puglieseweb.app.sample.order.adapter.broker.action.OrderAction;
import com.puglieseweb.app.sample.order.adapter.db.Order;

@Service
public class OrderService {

	@Autowired
	private OrderAction action;

	public String saveOrder(OrderRequest request) {
		// 1. convert OrderRequest to Order
		Order order = action.convertToOrder(request);

		// 2. save Order to database
		action.saveToDatabase(order);

		// 3. flatten the item & order as kafka message, and publish
		order.getItems().forEach(action::publishToKafka);

		// 4. return order number (auto generated)
		return order.getOrderNumber();
	}

}
