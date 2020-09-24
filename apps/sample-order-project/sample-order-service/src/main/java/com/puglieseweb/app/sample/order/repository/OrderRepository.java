package com.puglieseweb.app.sample.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puglieseweb.app.sample.order.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
