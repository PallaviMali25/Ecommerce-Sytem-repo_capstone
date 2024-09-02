package com.demo.eurekaClient1.serviceTest;

import com.eco.system.EcommerceSystemMain;
import com.eco.system.beans.PlaceOrderRequest;
import com.eco.system.entity.CartItem;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Order;
import com.eco.system.entity.Product;
import com.eco.system.exception.CartItemNotFoundException;
import com.eco.system.exception.OrderNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.OrderRepository;
import com.eco.system.serviceImplementation.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the OrderService.
 * This class tests the functionality of the OrderService class
 * using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository; // Mocked OrderRepository dependency

    @Mock
    private CartItemRepository cartItemRepository; // Mocked CartItemRepository dependency

    @InjectMocks
    private OrderService orderService; // Injected instance of OrderService with mocks

    /**
     * Test case for retrieving an order by its ID when the order exists.
     * It verifies that the service retrieves the correct order.
     */
    @Test
    void testGetOrderById_Success() {
        // Given: Mock the findById method to return an order
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // When: Call the getOrderById method
        Order foundOrder = orderService.getOrderById(1);

        // Then: Verify the results
        assertNotNull(foundOrder); // Ensure the order is not null
        verify(orderRepository, times(1)).findById(1); // Ensure findById was called exactly once
    }

    /**
     * Test case for retrieving an order by its ID when the order is not found.
     * It verifies that the service throws an OrderNotFoundException.
     */
    @Test
    void testGetOrderById_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1));
    }

    /**
     * Test case for placing an order successfully.
     * It verifies that the service creates a new order with the correct details
     * and deletes the associated cart item.
     */
    @Test
    void testPlaceOrder_Success() {
        // Given: Setup mock cart item
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1);

        Product product = new Product();
        product.setPrice(100);

        Customer customer = new Customer();

        cartItem.setProduct(product);
        cartItem.setCustomer(customer);

        // Setup place order request
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setCartItemId(1);
        request.setQuantity(2);

        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem)); // Mock cart item retrieval
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock order saving

        // When: Execute place order
        Order placedOrder = orderService.placeOrder(request);

        // Then: Verify the order has been placed correctly
        assertNotNull(placedOrder); // Ensure the order is not null
        assertEquals(customer, placedOrder.getCustomer()); // Verify the customer
        assertEquals(product, placedOrder.getProduct()); // Verify the product
        assertEquals(2, placedOrder.getQuantity()); // Verify the quantity
        assertEquals(200, placedOrder.getTotalPrice()); // Verify the total price (price * quantity)
        assertEquals("PLACED", placedOrder.getStatus()); // Verify the order status
        assertEquals(LocalDate.now(), placedOrder.getOrderDate()); // Verify the order date

        // Verify interactions
        verify(cartItemRepository, times(1)).findById(1); // Ensure findById was called exactly once
        verify(orderRepository, times(1)).save(any(Order.class)); // Ensure save was called exactly once
        verify(cartItemRepository, times(1)).delete(cartItem); // Ensure the cart item was deleted
    }

    /**
     * Test case for placing an order when the cart item is not found.
     * It verifies that the service throws a CartItemNotFoundException.
     */
    @Test
    void testPlaceOrder_CartItemNotFound() {
        // Given: Setup place order request
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setCartItemId(1);
        request.setQuantity(2);

        // Mock the findById method to return an empty result
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(CartItemNotFoundException.class, () -> orderService.placeOrder(request));
    }
}
