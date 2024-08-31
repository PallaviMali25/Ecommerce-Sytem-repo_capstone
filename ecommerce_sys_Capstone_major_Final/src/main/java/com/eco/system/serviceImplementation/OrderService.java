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

@Service // Indicates that this class is a Spring service component
public class OrderService implements OrderServiceIntf {

    @Autowired
    private OrderRepository orderRepository; // Injects OrderRepository to handle order-related database operations

    @Autowired
    private CartItemRepository cartItemRepository; // Injects CartItemRepository to handle cart item-related database operations
    
    /**
     * Retrieves an order by its ID.
     * 
     * @param orderId the ID of the order to retrieve
     * @return the order with the specified ID
     * @throws OrderNotFoundException if no order is found with the given ID
     */
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
    }

    /**
     * Places a new order based on the provided request.
     * 
     * @param placeOrderRequest contains the details required to place an order
     * @return the created order
     * @throws CartItemNotFoundException if no cart item is found with the given cart ID
     */
    public Order placeOrder(PlaceOrderRequest placeOrderRequest) {
        Integer cartId = placeOrderRequest.getCartItemId(); // Retrieve cart item ID from the request
        Integer quantity = placeOrderRequest.getQuantity(); // Retrieve quantity from the request
 

        // Retrieve the cart item based on the provided cart ID
        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found for the given cartId"));

        // Fetch associated product and customer details from the cart item
        Product product = cartItem.getProduct();
        Customer customer = cartItem.getCustomer();
      
        // Calculate total price for the order
        Integer totalPrice = product.getPrice() * quantity;

        // Create a new Order entity
        Order order = new Order();
        order.setCustomer(customer); // Set customer associated with the order
        order.setProduct(product); // Set product associated with the order
        order.setQuantity(quantity); // Set quantity of the product
        order.setTotalPrice(totalPrice); // Set total price of the order
        order.setStatus("PLACED"); // Set the status of the order
        order.setOrderDate(LocalDate.now()); // Set the current date as the order date
        
        // Remove the cart item after placing the order
        cartItemRepository.delete(cartItem);
    

        return orderRepository.save(order); // Save and return the created order
    }
}
