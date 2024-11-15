package com.warehouse.service;

import com.warehouse.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Iterable<Order> findAll();
    Optional<Order> findById(long id);
    Order save(Order order) throws Exception;
    int deleteById(long id) throws Exception;
    List<Order> findByArticleId(long id);
}
