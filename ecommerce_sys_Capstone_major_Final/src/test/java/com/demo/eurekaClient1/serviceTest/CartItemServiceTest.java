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
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.ProductRepository;
import com.eco.system.serviceImplementation.CartItemService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository; // Mocked CartItemRepository dependency
    
    @Mock
    private ProductRepository productRepository; // Mocked ProductRepository dependency

    @InjectMocks
    private CartItemService cartItemService; // Injected instance of CartItemService with mocks
    
    private CartItem cartItem;
    private Customer customer;
    private Product product;

    /**
     * Sets up common test data before each test.
     * This method initializes a customer, product, and cartItem instance.
     */
    @BeforeEach
    void setUp() {
        customer = new Customer(1, "John Doe", "john@example.com", "password", "123 Main St");
        product = new Product(1, "Product 1", "Category 1", 100, 10);
        cartItem = new CartItem(1, customer, product); // Initialize cartItem with the customer and product
    }

    /**
     * Test case for retrieving all cart items.
     * Verifies that the service retrieves the correct list of cart items.
     */
    @Test
    void testGetAllCartItems() {
        // Mock the findAll method to return a list containing the initialized cartItem
        when(cartItemRepository.findAll()).thenReturn(Arrays.asList(cartItem));

        // Call the getAllCartItems method
        List<CartItem> cartItems = cartItemService.getAllCartItems();

        // Verify the results
        assertNotNull(cartItems); // Ensure the result is not null
        assertEquals(1, cartItems.size()); // Ensure the list size is 1
        verify(cartItemRepository, times(1)).findAll(); // Verify findAll was called once
    }

    /**
     * Test case for retrieving a cart item by its ID.
     * Verifies that the service retrieves the correct cart item.
     */
    @Test
    void testGetCartItemById() {
        // Mock the findById method to return the initialized cartItem
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        // Call the getCartItemById method
        CartItem result = cartItemService.getCartItemById(1);

        // Verify the results
        assertNotNull(result); // Ensure the result is not null
        assertEquals(cartItem, result); // Ensure the result matches the mock cartItem
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
    }

    /**
     * Test case for retrieving a cart item by its ID when not found.
     * Verifies that the service throws a CartItemNotFoundException.
     */
    @Test
    void testGetCartItemByIdNotFound() {
        // Mock the findById method to return an empty Optional
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        // Verify that the service throws CartItemNotFoundException
        assertThrows(CartItemNotFoundException.class, () -> cartItemService.getCartItemById(1));
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
    }

    /**
     * Test case for retrieving cart items by customer ID.
     * Verifies that the service retrieves the correct list of cart items for the specified customer.
     */
    @Test
    void testGetCartItemsByCustomerId() {
        // Mock the findByCustomerCustomerId method to return a list containing the initialized cartItem
        when(cartItemRepository.findByCustomerCustomerId(1)).thenReturn(Arrays.asList(cartItem));

        // Call the getCartItemsByCustomerId method
        List<CartItem> cartItems = cartItemService.getCartItemsByCustomerId(1);

        // Verify the results
        assertNotNull(cartItems); // Ensure the result is not null
        assertEquals(1, cartItems.size()); // Ensure the list size is 1
        assertEquals(cartItem, cartItems.get(0)); // Ensure the result matches the mock cartItem
        verify(cartItemRepository, times(1)).findByCustomerCustomerId(1); // Verify findByCustomerCustomerId was called once
    }

    /**
     * Test case for adding a new cart item.
     * Verifies that the service correctly saves the cart item.
     */
    @Test
    void testAddCartItem() {
        // Create a CartItemDto instance with sample data
        CartItemDto cartItemDto = new CartItemDto(1, 1);

        // Mock the save method to return the initialized cartItem
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call the addCartItem method
        CartItem result = cartItemService.addCartItem(cartItemDto);

        // Verify the results
        assertNotNull(result); // Ensure the result is not null
        assertEquals(cartItem, result); // Ensure the result matches the mock cartItem
        verify(cartItemRepository, times(1)).save(any(CartItem.class)); // Verify save was called once
    }

    /**
     * Test case for updating an existing cart item.
     * Verifies that the service updates the cart item with the correct product details.
     */
    @Test
    void testUpdateCartItem() throws CartItemNotFoundException, ProductNotFoundException {
        // Prepare test data
        CartItemUpdatebean cartItemUpdatebean = new CartItemUpdatebean(2);
        Customer customer = new Customer(1, "Customer 1", "customer1@example.com", "12345678", "btm"); // Example customer
        Product existingProduct = new Product(1, "Product 1", "Category 1", 100, 10); // Existing product
        Product updatedProduct = new Product(2, "Product 2", "Category 2", 200, 5); // Updated product
        
        // Assuming CartItem constructor takes cartItemId, customer, and product
        CartItem cartItem = new CartItem(1, customer, existingProduct); // Initialize cartItem with existing product

        // Mock the repositories
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem)); // Mock cart item found by ID
        when(productRepository.findById(2)).thenReturn(Optional.of(updatedProduct)); // Mock updated product found by ID
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem); // Mock saving the updated cart item

        // Call the updateCartItem method
        CartItem result = cartItemService.updateCartItem(1, cartItemUpdatebean);

        // Validate the result
        assertNotNull(result); // Ensure the result is not null
        assertEquals(updatedProduct.getProductId(), result.getProduct().getProductId()); // Verify product ID is updated

        // Verify interactions with repositories
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
        verify(productRepository, times(1)).findById(2); // Verify findById was called on product repository
        verify(cartItemRepository, times(1)).save(cartItem); // Verify save was called once on cart item repository
    }

    /**
     * Test case for updating a cart item when it is not found.
     * Verifies that the service throws a CartItemNotFoundException.
     */
    @Test
    void testUpdateCartItemNotFound() {
        // Prepare test data
        CartItemUpdatebean cartItemUpdatebean = new CartItemUpdatebean(2);

        // Mock the findById method to return an empty Optional
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        // Verify that the service throws CartItemNotFoundException
        assertThrows(CartItemNotFoundException.class, () -> cartItemService.updateCartItem(1, cartItemUpdatebean));
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
    }

    /**
     * Test case for deleting an existing cart item.
     * Verifies that the service deletes the cart item.
     */
    @Test
    void testDeleteCartItem() {
        // Mock the findById method to return the initialized cartItem
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        // Call the deleteCartItem method
        cartItemService.deleteCartItem(1);

        // Verify that the cart item was deleted
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
        verify(cartItemRepository, times(1)).delete(cartItem); // Verify delete was called once
    }

    /**
     * Test case for deleting a cart item when it is not found.
     * Verifies that the service throws a CartItemNotFoundException.
     */
    @Test
    void testDeleteCartItemNotFound() {
        // Mock the findById method to return an empty Optional
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        // Verify that the service throws CartItemNotFoundException
        assertThrows(CartItemNotFoundException.class, () -> cartItemService.deleteCartItem(1));
        verify(cartItemRepository, times(1)).findById(1); // Verify findById was called once
    }
}
