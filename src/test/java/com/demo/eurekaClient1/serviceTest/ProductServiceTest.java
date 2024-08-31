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

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EcommerceSystemMain.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1);

        assertNotNull(foundProduct);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

  
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
        when(productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Call the service method
        Product updatedProduct = productService.updateProduct(1, productDTO);

        // Then: Validate the product has been updated correctly
        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals("Updated Category", updatedProduct.getCategory());
        assertEquals(200, updatedProduct.getPrice());
        assertEquals(50, updatedProduct.getStock());

        // Verify interactions
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);
    }



    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        ProductDTO productDTO = new ProductDTO();
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, productDTO));
    }

    @Test
    void testDeleteProduct_Success() throws ProductNotFoundException {
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(cartItemRepository, times(1)).deleteByProductProductId(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1));
    }
}
