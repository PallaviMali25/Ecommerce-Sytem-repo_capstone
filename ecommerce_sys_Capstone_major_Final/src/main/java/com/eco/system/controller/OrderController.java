package com.eco.system.controller;

// Import necessary classes and libraries
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

@RestController // Indicates that this class is a REST controller
@RequestMapping("/orders") // Base URL for all endpoints related to orders
@CrossOrigin(origins = "http://localhost:8081") // when request comes from this url, no error shown
public class OrderController {

    @Autowired // Injects the OrderService  for dependency injection
    private OrderService orderService;

    /**
     * POST endpoint to place a new order.
     * 
     * @param placeOrderRequest DTO containing the order placement details.
     * @return ResponseEntity containing the created Order and HTTP status.
     */
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) {
        Order order = orderService.placeOrder(placeOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED); // Returns 201 Created status indicating successful order placement
    }
    
    /**
     * GET endpoint to retrieve an order by its ID.
     * 
     * @param orderId ID of the order to retrieve.
     * @return ResponseEntity containing the Order and HTTP status.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK); // Returns 200 OK status with the retrieved order
    }
    
}
