package com.eco.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eco.system.beans.PlaceOrderRequest;
import com.eco.system.entity.Order;
import com.eco.system.serviceImplementation.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:8081")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) {
        Order order = orderService.placeOrder(placeOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
    	Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    
}