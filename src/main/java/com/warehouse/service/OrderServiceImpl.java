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
    public Order save(Order order) throws Exception {

        if(order.getId() == null){
            // id is in order
            Optional<String> result = articleService.updateStockById( order.getArticleId(), -order.getQuantity());
            if(result.isEmpty()){
                throw new Exception("Order niet opgeslagen: Artikel kon niet worden afgeboekt");
            }
            order = create( order);
        }else{
            Optional<Order> check = orderDAO.findById(order.getId());
            int toAddOrSubtract = check.get().getQuantity() - order.getQuantity();
            Optional<String> result = articleService.updateStockById( order.getArticleId(), toAddOrSubtract);
            if(result.isEmpty()){
                throw new Exception("Order niet opgeslagen: Artikel kon niet worden afgeboekt");
            }
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
    public int deleteById(long id) throws Exception {
        Optional<Order> order = orderDAO.findById( id);
        Optional<String> result = articleService.updateStockById( order.get().getArticleId(), order.get().getQuantity());
        if(result.isEmpty()){
            throw new Exception("Order niet verwijderd: Artikel kon niet worden afgeboekt");
        }
        return orderDAO.deleteById( id);
    }

    @Override
    public List<Order> findByArticleId(long articleId) {

        List<Order> orders = orderDAO.findByArticleId( articleId);

        return orders;
    }
}
