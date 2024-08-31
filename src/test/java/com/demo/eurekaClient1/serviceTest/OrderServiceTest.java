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

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetOrderById_Success() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1);

        assertNotNull(foundOrder);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1));
    }

    @Test
    void testPlaceOrder_Success() {
        // Setup mock cart item
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

        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute place order
        Order placedOrder = orderService.placeOrder(request);

        assertNotNull(placedOrder);
        assertEquals(customer, placedOrder.getCustomer());
        assertEquals(product, placedOrder.getProduct());
        assertEquals(2, placedOrder.getQuantity());
        assertEquals(200, placedOrder.getTotalPrice());
        assertEquals("PLACED", placedOrder.getStatus());
        assertEquals(LocalDate.now(), placedOrder.getOrderDate());

        verify(cartItemRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    void testPlaceOrder_CartItemNotFound() {
        // Setup place order request
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setCartItemId(1);
        request.setQuantity(2);

        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> orderService.placeOrder(request));
    }
}
