package com.puglieseweb.app.sample.order.command.service;

import com.puglieseweb.app.sample.order.api.request.OrderRequest;
import com.puglieseweb.app.sample.order.command.action.OrderAction;
import com.puglieseweb.app.sample.order.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	final static Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private OrderAction actionAction;

	public String saveOrder(OrderRequest request) {
		Order order = actionAction.convertToOrder(request);
		actionAction.saveToDatabase(order);
		LOGGER.debug("flatten the item & order as kafka message, and publish");
		order.getItems().forEach(actionAction::publishToKafka);
		return order.getOrderNumber();
	}

}
