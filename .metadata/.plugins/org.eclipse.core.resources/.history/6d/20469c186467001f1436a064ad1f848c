package com.eco.system.serviceImplementation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.PlaceOrderRequest;
import com.eco.system.entity.CartItem;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Order;
import com.eco.system.entity.Product;
import com.eco.system.exception.CartItemNotFoundException;
import com.eco.system.exception.OrderNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.OrderRepository;
import com.eco.system.serviceInterface.OrderServiceIntf;

@Service
public class OrderService implements OrderServiceIntf{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    
    public Order getOrderById(Integer orderId) {
        Order order= orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
       // order.getCustomer().setOrders(null);
        return order;
    }

    public Order placeOrder(PlaceOrderRequest placeOrderRequest) {
        Integer cartId = placeOrderRequest.getCartItemId();
        Integer quantity = placeOrderRequest.getQuantity();
        System.out.println("cart Id:"+cartId + "qty:"+quantity);

        // Retrieve the cart item based on cartId
        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found for the given cartId"));

        // Fetch associated product and customer details
        Product product = cartItem.getProduct();
        Customer customer = cartItem.getCustomer();
        System.out.println(product);
        System.out.println(customer);

        // Calculate total price
        Integer totalPrice = product.getPrice() * quantity;

        // Create and save the order
        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        
       // order.getCustomer().setOrders(null);
        // Remove the cart item after placing the order
        cartItemRepository.delete(cartItem);
        System.out.println("order details: "+order);

        return orderRepository.save(order);
    }
}