package com.demo.eurekaClient1.serviceTest;

import com.eco.system.EcommerceSystemMain;
import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.ProductRepository;
import com.eco.system.serviceImplementation.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the ProductService.
 * This class tests the functionality of the ProductService class
 * using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; // Mocked ProductRepository dependency

    @Mock
    private CartItemRepository cartItemRepository; // Mocked CartItemRepository dependency

    @InjectMocks
    private ProductService productService; // Injected instance of ProductService with mocks

    /**
     * Test case for retrieving all products.
     * It verifies that the service correctly retrieves all products.
     */
    @Test
    void testGetAllProducts() {
        // Given: Mock the findAll method to return a list of products
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // When: Call the getAllProducts method
        List<Product> products = productService.getAllProducts();

        // Then: Verify the results
        assertEquals(2, products.size()); // Verify the size of the returned product list
        verify(productRepository, times(1)).findAll(); // Ensure findAll was called exactly once
    }

    /**
     * Test case for retrieving a product by its ID when the product exists.
     * It verifies that the service retrieves the correct product.
     */
    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // Given: Mock the findById method to return a product
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When: Call the getProductById method
        Product foundProduct = productService.getProductById(1);

        // Then: Verify the results
        assertNotNull(foundProduct); // Ensure the product is not null
        verify(productRepository, times(1)).findById(1); // Ensure findById was called exactly once
    }

    /**
     * Test case for retrieving a product by its ID when the product is not found.
     * It verifies that the service throws a ProductNotFoundException.
     */
    @Test
    void testGetProductById_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1));
    }

    /**
     * Test case for creating a new product.
     * It verifies that the service saves the product correctly.
     */
    @Test
    void testCreateProduct() {
        // Given: Mock the save method to return the saved product
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        // When: Call the createProduct method
        Product savedProduct = productService.createProduct(product);

        // Then: Verify the results
        assertNotNull(savedProduct); // Ensure the saved product is not null
        verify(productRepository, times(1)).save(product); // Ensure save was called exactly once
    }

    /**
     * Test case for updating an existing product successfully.
     * It verifies that the service updates the product with new values.
     */
    @Test
    void testUpdateProduct_Success() throws ProductNotFoundException {
        // Given: Mock the existing product
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // And: Define the DTO with new values
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Updated Product");
        productDTO.setCategory("Updated Category");
        productDTO.setPrice(200);
        productDTO.setStock(50);

        // When: Mock the save method to return the updated product
        when(productRepository.save(product)).thenReturn(product);

        // Act: Call the service method
        Product updatedProduct = productService.updateProduct(1, productDTO);

        // Then: Validate the product has been updated correctly
        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals("Updated Category", updatedProduct.getCategory());
        assertEquals(200, updatedProduct.getPrice());
        assertEquals(50, updatedProduct.getStock());

        // Verify interactions
        verify(productRepository, times(1)).findById(1); // Ensure findById was called once
        verify(productRepository, times(1)).save(product); // Ensure save was called once
    }

    /**
     * Test case for updating a product that does not exist.
     * It verifies that the service throws a ProductNotFoundException.
     */
    @Test
    void testUpdateProduct_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        ProductDTO productDTO = new ProductDTO();
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, productDTO));
    }

    /**
     * Test case for deleting an existing product successfully.
     * It verifies that the service deletes the product and associated cart items correctly.
     */
    @Test
    void testDeleteProduct_Success() throws ProductNotFoundException {
        // Given: Mock the findById method to return a product
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When: Call the deleteProduct method
        productService.deleteProduct(1);

        // Then: Verify interactions
        verify(cartItemRepository, times(1)).deleteByProductProductId(1); // Ensure cart items are deleted
        verify(productRepository, times(1)).deleteById(1); // Ensure the product is deleted
    }

    /**
     * Test case for deleting a product that does not exist.
     * It verifies that the service throws a ProductNotFoundException.
     */
    @Test
    void testDeleteProduct_NotFound() {
        // Given: Mock the findById method to return an empty result
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Then: Verify that the service throws the appropriate exception
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1));
    }
}
