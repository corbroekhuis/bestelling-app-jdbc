package com.warehouse.service;

import com.warehouse.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Iterable<Order> findAll();
    Optional<Order> findById(long id);
    Order save(Order order);
    int deleteById(long id);
    List<Order> findByArticleId(long id);
}
