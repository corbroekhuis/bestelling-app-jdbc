package com.warehouse.service;

import com.warehouse.dao.order.OrderDAO;
import com.warehouse.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO;
    ArticleService articleService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ArticleService articleService) {
        this.orderDAO = orderDAO;
        this.articleService = articleService;
    }
    
    @Override
    public Iterable<Order> findAll() {

        List<Order> OrderS = new ArrayList<>();
        Iterable<Order> orders = orderDAO.findAll();
        for( Order order: orders){
            OrderS.add((Order) ( order));
        }
        return OrderS;

    }

    @Override
    public Optional<Order> findById(long id) {

        Optional<Order> order = orderDAO.findById( id);
        return Optional.ofNullable((Order) order.get());
    }

    @Override
    public Order save(Order order) {

        if(order.getId() == null){
            // id is in order
            order = create( order);
        }else{
            articleService.updateStockById( order.getArticleId(), order.getQuantity());
            int updated = orderDAO.update( order);
            System.out.println( "Updated: " + updated + " rows");
        }
        
        return  order;
    }

    private Order create(Order order) {

        Number id = orderDAO.create( order);
        System.out.println( "Created Order with id: " + id.toString());
        order.setId( id.longValue());
        return order;
    }

    @Override
    public int deleteById(long id) {
        Optional<Order> order = orderDAO.findById( id);
        articleService.updateStockById( order.get().getArticleId(), order.get().getQuantity());
        return orderDAO.deleteById( id);
    }

    @Override
    public List<Order> findByArticleId(long articleId) {

        List<Order> orders = orderDAO.findByArticleId( articleId);

        return orders;
    }
}
