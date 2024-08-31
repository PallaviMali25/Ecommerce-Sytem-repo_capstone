package com.eco.system.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.ProductRepository;
import com.eco.system.serviceInterface.ProductServiceIntf;

@Service
public class ProductService implements ProductServiceIntf {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductNotFoundException {
      //  Product product = getProductById(productId);
    	Product product =productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) throws ProductNotFoundException {
       // Product product = getProductById(productId);
    	Product product =productRepository.findById(productId)
    	        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    	  
    	//this line added beuse of Cannot delete or update a parent row: a foreign key constraint fails
    	cartItemRepository.deleteByProductProductId(productId);
    	    
    	    // Now delete the product
    	    productRepository.deleteById(productId);
    }
}