package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;

public interface ProductServiceIntf {

    List<Product> getAllProducts();

    Product getProductById(Integer productId) throws ProductNotFoundException;

    Product createProduct(Product product);

    Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductNotFoundException;

    void deleteProduct(Integer productId) throws ProductNotFoundException;
}
