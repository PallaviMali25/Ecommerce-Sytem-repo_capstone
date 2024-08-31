package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;

public interface CartItemServiceIntf{

    List<CartItem> getAllCartItems();

    CartItem getCartItemById(Integer cartItemId);

    List<CartItem> getCartItemsByCustomerId(Integer customerId);

    CartItem addCartItem(CartItemDto cartItemDto);

    CartItem updateCartItem(Integer cartItemId, CartItemUpdatebean updatedCartItem);

    void deleteCartItem(Integer cartItemId);
}
