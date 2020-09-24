package com.puglieseweb.app.sample.order.command.action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.puglieseweb.app.sample.order.api.request.OrderItemRequest;
import com.puglieseweb.app.sample.order.api.request.OrderRequest;
import com.puglieseweb.app.sample.order.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puglieseweb.app.sample.order.broker.message.OrderMessage;
import com.puglieseweb.app.sample.order.broker.producer.OrderProducer;
import com.puglieseweb.app.sample.order.entity.Order;
import com.puglieseweb.app.sample.order.entity.OrderItem;
import com.puglieseweb.app.sample.order.repository.OrderItemRepository;

@Component
public class OrderAction {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProducer orderProducer;

	public Order convertToOrder(OrderRequest request) {
		var result = new Order();

		result.setCreditCardNumber(request.getCreditCardNumber());
		result.setOrderLocation(request.getOrderLocation());
		result.setOrderDateTime(LocalDateTime.now());
		result.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

		List<OrderItem> items = request.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
		items.forEach(item -> item.setOrder(result));

		result.setItems(items);

		return result;
	}

	private OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
		var result = new OrderItem();

		result.setItemName(itemRequest.getItemName());
		result.setPrice(itemRequest.getPrice());
		result.setQuantity(itemRequest.getQuantity());

		return result;
	}

	public void publishToKafka(OrderItem item) {
		var orderMessage = new OrderMessage();

		orderMessage.setItemName(item.getItemName());
		orderMessage.setPrice(item.getPrice());
		orderMessage.setQuantity(item.getQuantity());

		orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
		orderMessage.setOrderLocation(item.getOrder().getOrderLocation());
		orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
		orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());

		orderProducer.publish(orderMessage);
	}

	public void saveToDatabase(Order order) {
		orderRepository.save(order);
		order.getItems().forEach(orderItemRepository::save);
	}

}
