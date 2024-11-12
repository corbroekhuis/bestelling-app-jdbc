package com.warehouse.dao.order;

import com.warehouse.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Iterable<Order> findAll();
    Optional<Order> findById(long id);
    Number create(Order article);
    int update(Order article);
    int deleteById(long id);
    List<Order> findByArticleId(long id);
}
