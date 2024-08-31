package com.eco.system.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Product;
import com.eco.system.exception.CartItemNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.serviceInterface.CartItemServiceIntf;

@Service
public class CartItemService implements CartItemServiceIntf{

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Integer cartItemId) {
        CartItem cartItem= cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));
        //  cartItem.getCustomer().setCartItems(null);
          return cartItem;
    }

    public List<CartItem> getCartItemsByCustomerId(Integer customerId) {
        List<CartItem> cartItems= cartItemRepository.findByCustomerCustomerId(customerId);
//        for(CartItem c:cartItems) {
//        	c.getCustomer().setCartItems(null);
//        }
        return cartItems;
    }
    

//    public CartItem addCartItem(CartItem cartItem) {
//        CartItem cart= cartItemRepository.save(cartItem);
//      //  cart.getCustomer().setCartItems(null);
//        return cart;
//    }
    
    
    public CartItem addCartItem(CartItemDto cartItemDto) {
    	//System.out.println("cartItemDto :"+cartItemDto);
    	
    	Product product=new Product(cartItemDto.getProductId());
    	Customer customer= new Customer(cartItemDto.getCustomerId());
    	CartItem cart= new CartItem();
         cart.setProduct(product);
         cart.setCustomer(customer);
         cart= cartItemRepository.save(cart);
       //  System.out.println(cart);
      //  cart.getCustomer().setCartItems(null);
        return cart;
    }

    public CartItem updateCartItem(Integer cartItemId, CartItemUpdatebean cartItemUpdatebean) {
        //CartItem cartItem = getCartItemById(cartItemId);
    	CartItem cartItem =cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));
//        cartItem.setProduct(updatedCartItem.getProduct());
//        cartItem.setCustomer(updatedCartItem.getCustomer());
//        return cartItemRepository.save(cartItem);
    	Product product = new Product(cartItemUpdatebean.getProductId());
    	
         cartItem.setProduct(product);
//         cartItem.setCustomer(customer);
         cartItem= cartItemRepository.save(cartItem);
         return cartItem;
    }

    public void deleteCartItem(Integer cartItemId) {
       // CartItem cartItem = getCartItemById(cartItemId);
    	CartItem cartItem =cartItemRepository.findById(cartItemId)
    	        .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
    }
}