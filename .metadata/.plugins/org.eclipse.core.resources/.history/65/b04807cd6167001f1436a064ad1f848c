package com.eco.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eco.system.beans.ApiResponse;
import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;
import com.eco.system.serviceImplementation.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart-items")
@CrossOrigin(origins = "http://localhost:8081")

@Validated
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Integer cartItemId) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public List<CartItem> getCartItemsByCustomerId(@PathVariable Integer customerId) {
        return cartItemService.getCartItemsByCustomerId(customerId);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        CartItem addedCartItem = cartItemService.addCartItem(cartItemDto);
        if(addedCartItem!=null) {
        	ApiResponse response= new ApiResponse("Item added in cart, product id:"+addedCartItem.getProduct().getProductId(), 200);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        }else {
          	ApiResponse response= new ApiResponse("Item not added", 400);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        }
    }
//    @PostMapping
//    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
//        CartItem addedCartItem = cartItemService.addCartItem(cartItem);
//        return new ResponseEntity<>(addedCartItem, HttpStatus.OK);
//    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Integer cartItemId,@Valid @RequestBody CartItemUpdatebean cartItemUpdatebean) {
        CartItem updatedCartItem = cartItemService.updateCartItem(cartItemId, cartItemUpdatebean);
        if(updatedCartItem!=null) {
        	ApiResponse response= new ApiResponse("Cart is Updated with product id: "+updatedCartItem.getProduct().getProductId(), 200);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        }else {
          	ApiResponse response= new ApiResponse("Item not added", 400);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        ApiResponse response= new ApiResponse("Cart item deleted ", 200);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }}