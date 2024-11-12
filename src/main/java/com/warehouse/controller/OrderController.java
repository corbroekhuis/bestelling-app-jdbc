package com.warehouse.controller;

import com.warehouse.model.Order;
import com.warehouse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET: http:/<port>/api/order
    @GetMapping( value = "/order", produces = "application/json")
    public ResponseEntity<Iterable<Order>> findAll(){

        Iterable<Order> order = orderService.findAll();

        return ResponseEntity.ok(order);
    }

    // POST: http:/<port>/api/order
    @PostMapping( value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> saveOrder(@RequestBody Order Order){

        Order saved = orderService.save( Order);

        return ResponseEntity.ok(saved);
    }

    // GET: http:/<port>/api/order/2
    @GetMapping( value = "/order/{id}", produces = "application/json")
    public ResponseEntity<Order> findById(@PathVariable long id){
        Optional<Order> order = orderService.findById( id);

        if(order.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.ok(order.get());
        }
    }

    // DELETE: http:/<port>/api/order?ean=123456789
    @DeleteMapping( value="/order/{id}")
    public ResponseEntity<String> deleteById( @PathVariable long id){

        orderService.deleteById( id);
        return ResponseEntity.ok("deleted");
    }
}
