package com.puglieseweb.app.sample.order.repository;

import com.puglieseweb.app.sample.order.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

}
