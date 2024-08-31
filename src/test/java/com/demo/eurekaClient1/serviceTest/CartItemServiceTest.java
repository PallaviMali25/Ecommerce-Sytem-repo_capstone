package com.demo.eurekaClient1.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.eco.system.EcommerceSystemMain;
import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Product;
import com.eco.system.exception.CartItemNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.serviceImplementation.CartItemService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private CartItem cartItem;
    private Customer customer;
    private Product product;

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "John Doe", "john@example.com", "password", "123 Main St");
        product = new Product(1, "Product 1", "Category 1", 100, 10);
        cartItem = new CartItem(1, customer, product);
    }

    @Test
    void testGetAllCartItems() {
        when(cartItemRepository.findAll()).thenReturn(Arrays.asList(cartItem));

        List<CartItem> cartItems = cartItemService.getAllCartItems();

        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals(cartItem, cartItems.get(0));

        verify(cartItemRepository, times(1)).findAll();
    }

    @Test
    void testGetCartItemById() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        CartItem result = cartItemService.getCartItemById(1);

        assertNotNull(result);
        assertEquals(cartItem, result);

        verify(cartItemRepository, times(1)).findById(1);
    }

    @Test
    void testGetCartItemByIdNotFound() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.getCartItemById(1));

        verify(cartItemRepository, times(1)).findById(1);
    }

    @Test
    void testGetCartItemsByCustomerId() {
        when(cartItemRepository.findByCustomerCustomerId(1)).thenReturn(Arrays.asList(cartItem));

        List<CartItem> cartItems = cartItemService.getCartItemsByCustomerId(1);

        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals(cartItem, cartItems.get(0));

        verify(cartItemRepository, times(1)).findByCustomerCustomerId(1);
    }

    @Test
    void testAddCartItem() {
        CartItemDto cartItemDto = new CartItemDto(1, 1);

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem result = cartItemService.addCartItem(cartItemDto);

        assertNotNull(result);
        assertEquals(cartItem, result);

        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void testUpdateCartItem() {
        CartItemUpdatebean cartItemUpdatebean = new CartItemUpdatebean(2);
        Product updatedProduct = new Product(2, "Product 2", "Category 2", 200, 5);

        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem result = cartItemService.updateCartItem(1, cartItemUpdatebean);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductId(), result.getProduct().getProductId());

        verify(cartItemRepository, times(1)).findById(1);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void testUpdateCartItemNotFound() {
        CartItemUpdatebean cartItemUpdatebean = new CartItemUpdatebean(2);

        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.updateCartItem(1, cartItemUpdatebean));

        verify(cartItemRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCartItem() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        cartItemService.deleteCartItem(1);

        verify(cartItemRepository, times(1)).findById(1);
        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    void testDeleteCartItemNotFound() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.deleteCartItem(1));

        verify(cartItemRepository, times(1)).findById(1);
    }
}
