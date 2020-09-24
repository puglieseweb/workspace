package com.puglieseweb.app.sample.order.adapter.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puglieseweb.app.sample.order.adapter.db.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
